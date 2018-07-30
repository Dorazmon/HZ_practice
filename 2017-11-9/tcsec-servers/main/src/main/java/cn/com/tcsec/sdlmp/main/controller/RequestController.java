package cn.com.tcsec.sdlmp.main.controller;

import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.com.tcsec.sdlmp.common.param.REGEX;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
@RestController
public class RequestController {
	private final static Logger logger = LoggerFactory.getLogger(RequestController.class);
	static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JedisPool jedisPool;

	@RequestMapping(value = "/down_report", method = RequestMethod.POST)
	public void getReportFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String access_token = request.getParameter("access_token");
		String task_id = request.getParameter("task_id");
		String report_type = request.getParameter("report_type");
		if (access_token == null || task_id == null || report_type == null) {
			response.sendError(404);
			return;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("access_token:" + access_token);
			logger.debug("task_id:" + task_id);
			logger.debug("report_type:" + report_type);
		}

		if (!match(access_token, REGEX.accessToken) || !match(access_token, REGEX.accessToken)
				|| !match(access_token, REGEX.accessToken)) {
			response.sendError(404);
			return;
		}

		String[] token = access_token.toString().split(":");
		Jedis jedis = jedisPool.getResource();
		String accessToken = jedis.get(token[0]);
		if (accessToken != null && accessToken.equals(token[1])) {
			jedis.expire(token[0], 60 * 60);
			jedis.close();
		} else {
			response.sendError(404);
			jedis.close();
			return;
		}

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("user_id", token[0]);
		requestHeaders.add("task_id", task_id);
		requestHeaders.add("report_type", report_type);

		HttpEntity<String> entity = new HttpEntity<String>(null, requestHeaders);

		URI uri = new URI("http://auth/down_report/");

		@SuppressWarnings("unchecked")
		Map<String, Object> map = restTemplate.postForObject(uri, entity, Map.class);
		if (map == null || map.isEmpty()) {
			response.sendError(404);
			return;
		}
		String fileName = (String) map.get("file_name");
		String fileData = (String) map.get("bytes");
		if (fileName == null || fileData == null) {
			response.sendError(404);
			return;
		}

		byte[] bytes = decoder.decodeBuffer(fileData);

		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("Content-Length", String.valueOf(bytes.length));
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
		response.setContentType("application/octet-stream; charset=utf-8");
		os.write(bytes);
		os.flush();
		os.close();
	}

	private boolean match(String str, String regex) {
		return Pattern.compile(regex).matcher(str).matches();
	}
}