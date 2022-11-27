package com.example.demo.util;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.vo.request.ServiceInvocation;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.VendorResponse;

@Component
public class Helper {
	
   public double getResponseTime(long startTime) {
	   return (System.currentTimeMillis() - startTime) / 1000.0;
   }
   
   public TranVO createTranVO() {
	   TranVO tranVO = new TranVO();
	   tranVO.setStartTime(System.currentTimeMillis());
	   tranVO.setProcCodeAndTime(new ConcurrentHashMap<>());
	   return tranVO;
   }
   
   public void addServiceDetails(TranVO tranVO, String processCode, double execTime, int statusCode) {
	   ServiceInvocation invocation = new ServiceInvocation();
	   invocation.setResponseTime(execTime);
	   invocation.setStatusCode(statusCode);
	   tranVO.getProcCodeAndTime().put(processCode, invocation);
   }
   
   public List<VendorResponse> joinAsync(List<CompletableFuture<VendorResponse>> compList) {
	   return compList.stream().map(CompletableFuture::join).collect(Collectors.toList());
   }
}
