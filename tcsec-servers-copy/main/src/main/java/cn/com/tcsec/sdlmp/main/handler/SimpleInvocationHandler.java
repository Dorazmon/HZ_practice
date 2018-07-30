package cn.com.tcsec.sdlmp.main.handler;

import java.lang.reflect.Method;

public class SimpleInvocationHandler extends AbstractInvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object obj = null;

		obj = getRestClient().send(method.getName(), args);

		return obj;
	}
}
