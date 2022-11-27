package com.example.demo.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
@Data
@RefreshScope
public class DemoConfig {
	
	@Value("${rest.connectionTimeout}")
	private long connectionTimeout;
	
	@Value("${rest.readTimeout}")
	private long readTimeout;
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

	    return builder.setConnectTimeout(Duration.ofMillis(connectionTimeout))
	     .setReadTimeout(Duration.ofMillis(readTimeout)).build();
	}

}
