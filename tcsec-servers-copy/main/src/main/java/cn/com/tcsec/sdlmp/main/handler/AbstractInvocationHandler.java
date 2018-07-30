package cn.com.tcsec.sdlmp.main.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.com.tcsec.sdlmp.common.client.RestClient;

public abstract class AbstractInvocationHandler implements InvocationHandler {

	private RestClient restClient;

	protected RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	@Override
	abstract public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
