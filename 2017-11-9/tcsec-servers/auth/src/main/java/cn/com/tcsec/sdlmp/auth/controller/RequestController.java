package cn.com.tcsec.sdlmp.auth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.tcsec.sdlmp.auth.mapper.AuthMapper;
import cn.com.tcsec.sdlmp.auth.report.PDFReportBuilder;
import cn.com.tcsec.sdlmp.auth.service.AuthService;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
@RestController
public class RequestController {
	private final static Logger logger = LoggerFactory.getLogger(RequestController.class);
	static BASE64Encoder encoder = new sun.misc.BASE64Encoder();

	@Autowired
	AuthService authService;
	@Autowired
	AuthMapper authMapper;

	@RequestMapping(value = "/down_report", method = RequestMethod.POST)
	public Object getReportFile(HttpServletRequest request, HttpServletResponse response) {
		logger.info("user_id:" + request.getHeader("user_id"));
		logger.info("task_id:" + request.getHeader("task_id"));
		logger.info("report_type:" + request.getHeader("report_type"));
		String user_id = request.getHeader("user_id");
		String task_id = request.getHeader("task_id");
		String report_type = request.getHeader("report_type");

		if (!authService.getAuthTaskIdRead(user_id, task_id)) {
			return null;
		}

		if ("pdf".equals(report_type)) {
			Map<String, Object> map = new HashMap<>();
			byte[] bytes = null;
			PDFReportBuilder reportBuilderImpl = new PDFReportBuilder(authMapper, task_id);
			try {
				bytes = reportBuilderImpl.builder();
			} catch (Exception e) {
				logger.error("reportBuilderImpl.builder();", e);
				return null;
			}
			if (bytes == null || bytes.length == 0) {
				logger.error("reportBuilderImpl.builder() 输出空结果！");
				return null;
			}

			map.put("file_name", reportBuilderImpl.getProject_name() + ".pdf");
			map.put("bytes", encoder.encodeBuffer(bytes));

			return map;
		}
		return null;
	}

}
