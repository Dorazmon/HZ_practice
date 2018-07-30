package cn.com.tcsec.sdlmp.cluster.registry;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

//				logger.info(request.getDispatcherType().name());

				chain.doFilter(request, response);
			}

			@Override
			public void destroy() {
			}
		};
		return fff;
	}

}
