package cn.com.tcsec.sdlmp.main;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import cn.com.tcsec.sdlmp.common.api.AuthServerAPI;
import cn.com.tcsec.sdlmp.common.api.NotifyServerAPI;
import cn.com.tcsec.sdlmp.common.api.SearchServerAPI;
import cn.com.tcsec.sdlmp.common.api.UserServerAPI;
import cn.com.tcsec.sdlmp.common.client.RestClient;
import cn.com.tcsec.sdlmp.common.filter.CommonErrorResolver;
import cn.com.tcsec.sdlmp.common.filter.CommonInvocationListener;
import cn.com.tcsec.sdlmp.common.filter.JsonRpcErrorAttributes;
import cn.com.tcsec.sdlmp.main.JsonServer.JsonRpcServiceExporter;
import cn.com.tcsec.sdlmp.main.handler.SimpleInvocationHandler;
import redis.clients.jedis.JedisPool;

@RefreshScope
@Configuration
public class ApplicationConfig {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	@Bean
	public JedisPool redisPoolFactory(@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") int port) {
		return new JedisPool(host, port);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		RestTemplate rest = new RestTemplate();
		return rest;
	}

	@Bean
	public RestClient restClient() throws Throwable {
		RestClient restClient = new RestClient();
		restClient.addRemoteInterface(AuthServerAPI.class);
		restClient.addRemoteInterface(UserServerAPI.class);
		restClient.addRemoteInterface(NotifyServerAPI.class);
		restClient.addRemoteInterface(SearchServerAPI.class);
		return restClient;
	}

	@Bean("/main")
	public static JsonRpcServiceExporter jsonRpcServiceExporter() throws Throwable {
		JsonRpcServiceExporter exp = new JsonRpcServiceExporter();
		exp.setAllowExtraParams(true);
		exp.setAllowLessParams(true);
		exp.setErrorResolver(new CommonErrorResolver());
		exp.setInvocationListener(new CommonInvocationListener());
		exp.addRemoteInterface(AuthServerAPI.class);
		exp.addRemoteInterface(UserServerAPI.class);
		exp.addRemoteInterface(NotifyServerAPI.class);
		exp.addRemoteInterface(SearchServerAPI.class);
		exp.setProxyInvoker(new SimpleInvocationHandler());

		return exp;
	}

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); // 1
		corsConfiguration.addAllowedHeader("*"); // 2
		corsConfiguration.addAllowedMethod("*"); // 3
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4
		return new CorsFilter(source);
	}

	@Bean
	public JsonRpcErrorAttributes jsonRpcErrorAttributes() {
		return new JsonRpcErrorAttributes();
	}

	@Bean
	public Filter testFilter() {
		Filter fff = new Filter() {

			@Override
			public void init(FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {

				chain.doFilter(request, response);
			}

			@Override
			public void destroy() {
			}
		};
		return fff;
	}

}
