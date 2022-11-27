package com.example.demo.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VendorResponse {
	
	private String source;
	@JsonProperty("isValid")
	private boolean isValid;
	
}
