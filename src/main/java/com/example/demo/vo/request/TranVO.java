package com.example.demo.vo.request;

import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

@Data
public class TranVO {
	
	private long startTime = System.currentTimeMillis();
	private ConcurrentHashMap<String, ServiceInvocation> procCodeAndTime;
	private double totalResponseTime;

}
