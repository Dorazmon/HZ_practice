package cn.com.tcsec.sdlmp.common.client;

import java.util.Map;

public class RestClient extends AbstractRestClient {

	public Object send(String methodName, Object[] args) throws Throwable {
		return doSend(methodName, args);
	}

	public <T> T send(String methodName, Map<String, Object> paramMap, Class<T> clazz) throws Throwable {
		return doSend(methodName, paramMap, clazz);
	}
}
