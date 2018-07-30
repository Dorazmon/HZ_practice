package cn.com.tcsec.sdlmp.main.JsonServer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import com.googlecode.jsonrpc4j.JsonRpcServer;

public class JsonRpcServiceExporter extends AbstractJsonServiceExporter implements HttpRequestHandler {

	private JsonRpcServer jsonRpcServer;

	protected void exportService() {
		jsonRpcServer = getJsonRpcServer();
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		jsonRpcServer.handle(request, response);
		response.getOutputStream().flush();
	}
}
