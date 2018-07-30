package cn.com.tcsec.sdlmp.main.JsonServer;

import java.lang.reflect.Proxy;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.ConvertedParameterTransformer;
import com.googlecode.jsonrpc4j.ErrorResolver;
import com.googlecode.jsonrpc4j.HttpStatusCodeProvider;
import com.googlecode.jsonrpc4j.InvocationListener;
import com.googlecode.jsonrpc4j.JsonRpcServer;

import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.main.handler.AbstractInvocationHandler;

/**
 * 抄袭自 com.googlecode.jsonrpc4j.spring.AbstractJsonServiceExporter
 * 这个类只有包权限，只能复制一份出来了
 * 
 * @author xk
 */
class AbstractJsonServiceExporter implements InitializingBean, ApplicationContextAware {
	private final static Logger logger = LoggerFactory.getLogger(AbstractJsonServiceExporter.class);
	private ObjectMapper objectMapper;
	private JsonRpcServer jsonRpcServer;
	private ApplicationContext applicationContext;
	private ErrorResolver errorResolver = null;
	private boolean backwardsCompatible = true;
	private boolean rethrowExceptions = false;
	private boolean allowExtraParams = false;
	private boolean allowLessParams = false;
	private boolean shouldLogInvocationErrors = true;
	private InvocationListener invocationListener = null;
	private HttpStatusCodeProvider httpStatusCodeProvider = null;
	private ConvertedParameterTransformer convertedParameterTransformer = null;
	private String contentType = null;
	private Set<Class<?>> interfaces;
	private AbstractInvocationHandler abstractInvocationHandler;

	public void addRemoteInterface(Class<?> clazz) throws Throwable {
		if (interfaces == null) {
			interfaces = new LinkedHashSet<>();
		}
		this.interfaces.add(clazz);
	}

	public void setProxyInvoker(AbstractInvocationHandler abstractInvocationHandler) throws Throwable {
		this.abstractInvocationHandler = abstractInvocationHandler;
	}

	protected Object getProxyForService(Set<Class<?>> interfaces) throws Exception {
		if (applicationContext != null) {
			if (applicationContext.containsBean("restClient")) {
				abstractInvocationHandler.setRestClient((RestClient) applicationContext.getBean("restClient"));
			}
		}

		return Proxy.newProxyInstance(abstractInvocationHandler.getClass().getClassLoader(),
				interfaces.toArray(new Class<?>[interfaces.size()]), abstractInvocationHandler);
	}

	public void afterPropertiesSet() throws Exception {

		if (objectMapper == null && applicationContext != null && applicationContext.containsBean("objectMapper")) {
			objectMapper = (ObjectMapper) applicationContext.getBean("objectMapper");
		}
		if (objectMapper == null && applicationContext != null) {
			try {
				objectMapper = BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, ObjectMapper.class);
			} catch (Exception e) {
				logger.debug("Exception ", e);
			}
		}
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}

		jsonRpcServer = new JsonRpcProxyServer(objectMapper, getProxyForService(interfaces),
				interfaces.toArray(new Class<?>[interfaces.size()]));

		jsonRpcServer.setErrorResolver(errorResolver);
		jsonRpcServer.setBackwardsCompatible(backwardsCompatible);
		jsonRpcServer.setRethrowExceptions(rethrowExceptions);
		jsonRpcServer.setAllowExtraParams(allowExtraParams);
		jsonRpcServer.setAllowLessParams(allowLessParams);
		jsonRpcServer.setInvocationListener(invocationListener);
		jsonRpcServer.setHttpStatusCodeProvider(httpStatusCodeProvider);
		jsonRpcServer.setConvertedParameterTransformer(convertedParameterTransformer);
		jsonRpcServer.setShouldLogInvocationErrors(shouldLogInvocationErrors);

		if (contentType != null) {
			jsonRpcServer.setContentType(contentType);
		}

		exportService();
	}

	void exportService() throws Exception {
	}

	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	JsonRpcServer getJsonRpcServer() {
		return jsonRpcServer;
	}

	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setErrorResolver(ErrorResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	public void setBackwardsCompatible(boolean backwardsCompatible) {
		this.backwardsCompatible = backwardsCompatible;
	}

	public void setRethrowExceptions(boolean rethrowExceptions) {
		this.rethrowExceptions = rethrowExceptions;
	}

	public void setAllowExtraParams(boolean allowExtraParams) {
		this.allowExtraParams = allowExtraParams;
	}

	public void setAllowLessParams(boolean allowLessParams) {
		this.allowLessParams = allowLessParams;
	}

	public void setInvocationListener(InvocationListener invocationListener) {
		this.invocationListener = invocationListener;
	}

	public void setHttpStatusCodeProvider(HttpStatusCodeProvider httpStatusCodeProvider) {
		this.httpStatusCodeProvider = httpStatusCodeProvider;
	}

	public void setConvertedParameterTransformer(ConvertedParameterTransformer convertedParameterTransformer) {
		this.convertedParameterTransformer = convertedParameterTransformer;
	}

	public void setShouldLogInvocationErrors(boolean shouldLogInvocationErrors) {
		this.shouldLogInvocationErrors = shouldLogInvocationErrors;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
