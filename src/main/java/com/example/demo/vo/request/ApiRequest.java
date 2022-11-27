package com.example.demo.vo.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ApiRequest {
	@NotEmpty
	private String employeeId;
	private List<String> sources;
	
}
