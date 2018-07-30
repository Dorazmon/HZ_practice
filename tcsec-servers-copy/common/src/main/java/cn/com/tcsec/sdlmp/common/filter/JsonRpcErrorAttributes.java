package cn.com.tcsec.sdlmp.common.filter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

/**
 * 定制错误应答报文
 * 
 * @author xiongkui
 *
 */
@Component
public class JsonRpcErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Map<String, Object> error = new LinkedHashMap<String, Object>();

		result.put("jsonrpc", "2.0");
		Object jsonErrorCode = requestAttributes.getAttribute("JsonRpc.Error.Code", RequestAttributes.SCOPE_REQUEST);
		if (jsonErrorCode != null) {
			error.put("code", jsonErrorCode);
			error.put("message",
					requestAttributes.getAttribute("JsonRpc.Error.Message", RequestAttributes.SCOPE_REQUEST));
			Object jsonErrorData = requestAttributes.getAttribute("JsonRpc.Error.Data",
					RequestAttributes.SCOPE_REQUEST);
			if (jsonErrorData != null) {
				error.put("data", jsonErrorData);
			}
			result.put("jsonrpc",
					requestAttributes.getAttribute("JsonRpc.Result.Jsonrpc", RequestAttributes.SCOPE_REQUEST));
			result.put("id", requestAttributes.getAttribute("JsonRpc.Result.Id", RequestAttributes.SCOPE_REQUEST));
		} else {
			result.put("jsonrpc", null);
			result.put("id", null);
			error.put("code", "-32004");
			error.put("message", "server error");
		}

		result.put("error", error);
		return result;
	}
}
