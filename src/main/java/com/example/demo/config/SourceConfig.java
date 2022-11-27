package com.example.demo.config;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties
@Data
public class SourceConfig {

	@NotNull
	private List<Map<String, String>> sources;
	
}
