package cn.com.tcsec.sdlmp.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;

import cn.com.tcsec.sdlmp.common.api.AuthServerAPI;
import cn.com.tcsec.sdlmp.common.api.NotifyServerAPI;
import cn.com.tcsec.sdlmp.common.api.UserServerAPI;
import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.common.filter.CommonErrorResolver;
import cn.com.tcsec.sdlmp.common.filter.CommonInvocationListener;
import redis.clients.jedis.JedisPool;

@Configuration
public class ApplicationConfig {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	@Bean
	public Filter testFilter() {
		Filter fff = new Filter() {

			@Override
			public void init(FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				logger.info("request from  {}", request.getRemoteHost());

				chain.doFilter(request, response);
			}

			@Override
			public void destroy() {
			}
		};
		return fff;
	}

	@Bean
	public JedisPool redisPoolFactory(@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") int port) {
		return new JedisPool(host, port);
	}

	@Bean
	public Object activeMQConnectionFactoryConfig(@Autowired ActiveMQConnectionFactory activeMQConnectionFactory) {
		activeMQConnectionFactory.setTrustedPackages(
				new ArrayList<String>(Arrays.asList("cn.com.tcsec.sdlmp.common.entity,java.util".split(","))));
		return null;
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RestClient restClient() throws Throwable {
		RestClient restClient = new RestClient();
		restClient.addRemoteInterface(AuthServerAPI.class);
		restClient.addRemoteInterface(UserServerAPI.class);
		restClient.addRemoteInterface(NotifyServerAPI.class);
		return restClient;
	}

	@Bean
	public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
		AutoJsonRpcServiceImplExporter exp = new AutoJsonRpcServiceImplExporter();
		exp.setAllowExtraParams(true);
		exp.setAllowLessParams(true);
		exp.setErrorResolver(new CommonErrorResolver());
		exp.setInvocationListener(new CommonInvocationListener());
		return exp;
	}
}
