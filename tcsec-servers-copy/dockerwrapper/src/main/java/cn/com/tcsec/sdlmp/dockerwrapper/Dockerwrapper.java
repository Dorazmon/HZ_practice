package cn.com.tcsec.sdlmp.dockerwrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Dockerwrapper {
	public static void main(String[] args) {
		SpringApplication.run(Dockerwrapper.class, args);
	}
}
