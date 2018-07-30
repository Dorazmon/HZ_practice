package cn.com.tcsec.sdlmp.common.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class HttpUtil {
	private HttpUtil() {
		super();
	}

	static ObjectMapper mapper = new ObjectMapper();

	public static Object sendJson(String url, String method, Map<String, Object> paramMap, Type returnType)
			throws Exception, Throwable {

		JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(url));

		Object object = client.invoke(method, paramMap, returnType);

		return object;
	}

	// 第三方登录
	public static String doPost(String url, CloseableHttpClient httpClient, String postData) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (postData != null && postData.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(postData, "UTF-8");
				stringEntity.setContentType("application/json;charset=utf-8");
				httpPost.setEntity(stringEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		CloseableHttpResponse response = null;
		String responseContent = null;
		try {
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "utf-8");
				EntityUtils.consume(entity);
			} else {
				throw new Exception("HTTP Request is not success, Response code is " + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	public static void main(String[] args) throws Exception {
		doGetForOauth("https://api.github.com/user?access_token=" + "1300836acad82cdd833917c92f81daed044f5b84");
		// doGetForOauth("https://github.com/login/oauth/access_token?client_id=97a3a79485eba972ef9b&client_secret"
		// +
		// "=8e253c173263ee3e1734cfe37b1dd285132baf69&redirect_uri=http://192.168.0.49:3000/codesec/user/"
		// + "thirdlogin&code=" + "fce53ebb40816037656b");
	}

	public static JsonNode doGetForOauth(String url) throws Exception {
		return doGetForOauth(url, HttpClients.custom().build());
	}

	private static JsonNode doGetForOauth(String url, CloseableHttpClient httpClient) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

		CloseableHttpResponse response = null;

		try {
			response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();

			return mapper.readTree(EntityUtils.toString(entity));
			// return null;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
