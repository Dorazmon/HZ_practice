package cn.com.tcsec.sdlmp.main.JsonServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;

public class JsonRpcProxyServer extends JsonRpcServer {

	private Class<?>[] remoteInterface;

	public JsonRpcProxyServer(ObjectMapper mapper, Object handler, Class<?>[] remoteInterface) {
		super(mapper, handler);
		this.remoteInterface = remoteInterface;
	}

	protected Class<?>[] getHandlerInterfaces(final String serviceName) {
		return remoteInterface;
	}

}
