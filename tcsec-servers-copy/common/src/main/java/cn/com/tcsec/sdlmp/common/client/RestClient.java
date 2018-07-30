package cn.com.tcsec.sdlmp.common.client;

import java.util.Map;

public class RestClient extends AbstractRestClient {

	public Object send(String methodName, Object[] args) throws Throwable {
		return doSend(methodName, args);
	}

	public Object send(String methodName, Map<String, Object> paramMap) throws Throwable {
		return doSend(methodName, paramMap);
	}
}
