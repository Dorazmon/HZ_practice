package cn.com.tcsec.sdlmp.common.filter;

import java.lang.reflect.Method;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.googlecode.jsonrpc4j.ErrorResolver;

import cn.com.tcsec.sdlmp.common.exception.ErrorContent;

public class CommonErrorResolver implements ErrorResolver {
//	private final static Logger logger = LoggerFactory.getLogger(CommonErrorResolver.class);

	@Override
	public JsonError resolveError(Throwable t, Method method, List<JsonNode> arguments) {
		return ErrorContent.SERVER_ERROR;
	}

}
