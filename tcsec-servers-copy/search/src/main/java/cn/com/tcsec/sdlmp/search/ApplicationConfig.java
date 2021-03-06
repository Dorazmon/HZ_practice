package cn.com.tcsec.sdlmp.search;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

@RefreshScope
@Configuration
public class ApplicationConfig {

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
		RestTemplate rest = new RestTemplate();
		return rest;
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
