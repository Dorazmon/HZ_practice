package cn.com.tcsec.sdlmp.common.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.googlecode.jsonrpc4j.ErrorResolver.JsonError;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.googlecode.jsonrpc4j.spring.rest.JsonRpcRestClient;

import cn.com.tcsec.sdlmp.common.annotation.AccessControl;
import cn.com.tcsec.sdlmp.common.annotation.ParamCtrl;
import cn.com.tcsec.sdlmp.common.exception.ErrorContent;
import cn.com.tcsec.sdlmp.common.param.REGEX;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

abstract public class AbstractRestClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	protected JedisPool jedisPool;

	private Map<String, MethodAttribute> methodMap;

	public void addRemoteInterface(Set<Class<?>> clazzs) throws Exception {
		for (Class<?> cls : clazzs) {
			addRemoteInterface(cls);
		}
	}

	public void addRemoteInterface(Class<?> clazz) throws Exception {
		JsonRpcService jsonRpcServiceAnnotation = clazz.getAnnotation(JsonRpcService.class);
		if (jsonRpcServiceAnnotation == null || jsonRpcServiceAnnotation.value() == null) {
			throw new Exception("远程接口没有正确使用  @JsonRpcService 注解");
		}

		if (methodMap == null) {
			methodMap = new LinkedHashMap<>();
		}

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (methodMap.containsKey(method.getName())) {
				throw new Exception("方法重复！");
			}

			MethodAttribute methodAttribute = new MethodAttribute();
			methodAttribute.list = new ArrayList<>();
			Annotation[] methodAnnotations = method.getAnnotations();
			for (Annotation annotation : methodAnnotations) {
				if (annotation instanceof AccessControl) {
					methodAttribute.accessControl = (AccessControl) annotation;
				}
			}
			if (methodAttribute.accessControl == null) {
				throw new Exception("方法【" + method.getName() + "】没有 AccessControl 注解");
			}

			Annotation[][] annotationss = method.getParameterAnnotations();
			Class<?>[] types = method.getParameterTypes();
			for (int i = 0; i < types.length; i++) {
				Annotation[] annotations = annotationss[i];
				JsonRpcParam jsonRpcParam = null;
				ParamCtrl paramCtrl = null;
				for (Annotation annotation : annotations) {
					if (annotation instanceof JsonRpcParam) {
						jsonRpcParam = (JsonRpcParam) annotation;
					}
					if (annotation instanceof ParamCtrl) {
						paramCtrl = (ParamCtrl) annotation;
					}
				}
				if (jsonRpcParam == null || paramCtrl == null || jsonRpcParam.value() == null
						|| paramCtrl.regex() == null) {
					throw new Exception("方法参数注解未正确设置！");
				}
				ParamAttribute paramAttribute = new ParamAttribute();
				paramAttribute.name = jsonRpcParam.value();
				paramAttribute.regex = paramCtrl.regex();
				paramAttribute.type = types[i];
				paramAttribute.must = paramCtrl.must();
				paramAttribute.ckeckSqlInject = paramCtrl.ckeckSqlInject();
				if (!paramAttribute.must) {
					methodAttribute.paramCountLimit++;
				}
				methodAttribute.list.add(paramAttribute);
			}
			if (!jsonRpcServiceAnnotation.value().startsWith("/")) {
				methodAttribute.serverName = "/".concat(jsonRpcServiceAnnotation.value());
			} else {
				methodAttribute.serverName = jsonRpcServiceAnnotation.value();
			}

			methodMap.put(method.getName(), methodAttribute);
		}
	}

	protected Object doSend(String methodName, Object[] args) throws Throwable {
		MethodAttribute methodAttribute = methodMap.get(methodName);
		Map<String, Object> paramMap = new HashMap<>();

		Object obj = null;
		for (int i = 0; i < methodAttribute.list.size(); i++) {
			ParamAttribute paramAttribute = methodAttribute.list.get(i);
			if ((obj = checkParams(args[i], paramAttribute)) != null) {
				return obj;
			}

			if ("access_token".equals(paramAttribute.name) && paramAttribute.must == true) {
				if ((obj = checkAccessToken(args[i], paramAttribute)) != null) {
					return obj;
				}
			}
			paramMap.put(paramAttribute.name, args[i]);
		}

		if ((obj = checkAccessControl(methodAttribute, args, methodName)) != null) {
			return obj;
		}

		obj = doSend(methodName, paramMap);

		return obj;
	}

	private Object checkAccessControl(MethodAttribute methodAttribute, Object[] args, String methodName)
			throws Exception {
		AccessControl accessControl = methodAttribute.accessControl;
		if (accessControl == null) {
			// TODO
			return null;
		}

		if (accessControl.enable()) {
			String request_key = null;
			request_key = (accessControl.ip() ? "ip" : "") + (accessControl.session() ? "session" : "")
					+ (accessControl.method() ? methodName : "");
			if (accessControl.paramIndex() != null && accessControl.paramIndex().length > 0) {
				Set<Integer> set = new HashSet<>(accessControl.paramIndex().length);
				for (Integer index : accessControl.paramIndex()) {
					if ((index) > methodAttribute.list.size() || index == 0) {
						throw new Exception("方法[" + methodName + "]配置了错误的AccessControl注释：paramIndex：超出下标范围");
					}
					set.add(index);
				}
				for (Integer index : set) {
					request_key = request_key + args[index - 1];
				}
			}
			Jedis jedis = jedisPool.getResource();
			jedis.select(2);
			Long count = jedis.incr(request_key);
			if (jedis.ttl(request_key) == -1) {
				jedis.expire(request_key, accessControl.cycle());
			}
			jedis.close();

			if (count > accessControl.count()) {
				if (accessControl.handler() == AccessControl.Handler.reject) {
					return ErrorContent.REQUEST_OVER_LIMIT;
				}
				if (accessControl.handler() == AccessControl.Handler.auth_code) {
					return ErrorContent.REQUEST_OVER_LIMIT;
				}
			}

			// TODO
			// handler;
		}
		return null;
	}

	protected Object doSend(String methodName, Map<String, Object> paramMap) throws Throwable {
		String serverName = methodMap.get(methodName).serverName;

		Object obj = null;
		JsonRpcRestClient client = new JsonRpcRestClient(new URL("http:/" + serverName + serverName + ""),
				restTemplate);

		obj = client.invoke(methodName, paramMap, Object.class);

		return obj;
	}

	@SuppressWarnings("unused")
	private JsonError check(String methodName, Object[] args, boolean checkParam, boolean checkAccessToken) {
		MethodAttribute methodAttribute = methodMap.get(methodName);

		JsonError obj = null;
		for (int i = 0; i < methodAttribute.list.size(); i++) {
			ParamAttribute paramAttribute = methodAttribute.list.get(i);
			if ((obj = checkParams(args[i], paramAttribute)) != null) {
				return obj;
			}

			if ("access_token".equals(paramAttribute.name) && checkAccessToken == true && paramAttribute.must == true) {
				if ((obj = checkAccessToken(obj, paramAttribute)) != null) {
					return obj;
				}
			}
		}

		return obj;
	}

	private JsonError checkAccessToken(Object arg, ParamAttribute paramAttribute) {
		String[] token = arg.toString().split(":");

		Jedis jedis = jedisPool.getResource();
		List<String> tokenList = jedis.hmget("user-accessToken", token[0]);
		jedis.close();
		if (!tokenList.isEmpty() && tokenList.get(0) != null && tokenList.get(0).equals(token[1])) {
			return null;
		}
		return ErrorContent.SYSTEM_REJECT;
	}

	private JsonError checkParams(Object arg, ParamAttribute paramAttribute) {
		if (paramAttribute.type.isArray()) {
			String[] strings = (String[]) arg;
			if (strings == null || (strings.length == 0 && paramAttribute.must)) {
				return ErrorContent.METHOD_PARAMS_INVALID;
			}
		} else if (paramAttribute.must && arg == null) {
			return ErrorContent.METHOD_PARAMS_INVALID;
		}

		if (arg == null) {
			// limit.set(limit.get() + 1);
			return null;
		}

		if (paramAttribute.type.isArray()) {
			for (String str : (String[]) arg) {
				if (!match(str, paramAttribute.regex)) {
					return ErrorContent.METHOD_PARAMS_INVALID;
				}

				if (paramAttribute.ckeckSqlInject) {
					if (match(arg.toString(), REGEX.ckeckSqlInject)) {
						return ErrorContent.METHOD_PARAMS_INVALID;
					}
				}
			}
		} else {
			if (!match(arg.toString(), paramAttribute.regex)) {
				return ErrorContent.METHOD_PARAMS_INVALID;
			}

			if (paramAttribute.ckeckSqlInject) {
				if (match(arg.toString(), REGEX.ckeckSqlInject)) {
					return ErrorContent.METHOD_PARAMS_INVALID;
				}
			}
		}

		return null;
	}

	private boolean match(String str, String regex) {
		return Pattern.compile(regex).matcher(str).matches();
	}

	protected class MethodAttribute {
		protected String serverName;
		protected int paramCountLimit = 0;
		protected AccessControl accessControl;
		protected List<ParamAttribute> list;
	}

	protected class ParamAttribute {
		protected String name;
		protected String regex;
		protected Class<?> type;
		protected boolean must;
		protected boolean ckeckSqlInject;
	}
}
