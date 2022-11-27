package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import com.example.demo.config.SourceConfig;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableRetry
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	ApplicationRunner applicationRunner(SourceConfig config) {
		return args-> log.info("Properties : {}" + config);
	}

}
