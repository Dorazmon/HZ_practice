package cn.com.tcsec.sdlmp.dockerwrapper;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerwrapperConfig {

	@Bean
	public Object activeMQConnectionFactoryConfig(@Autowired ActiveMQConnectionFactory activeMQConnectionFactory) {
		activeMQConnectionFactory.setTrustedPackages(
				new ArrayList<String>(Arrays.asList("cn.com.tcsec.sdlmp.common.entity,java.util".split(","))));
		return null;
	}
}