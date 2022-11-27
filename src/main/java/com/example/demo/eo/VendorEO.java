package com.example.demo.eo;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exception.CustomException;
import com.example.demo.util.Helper;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.SourceResponse;
import com.example.demo.vo.response.VendorResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class VendorEO implements IVendorEO{
	
	private final RestTemplate template;
	private final Helper helper;
	
	
	/**
	 * Method to invoke datasource
	 * @param emplId
	 * @param source
	 * @param tranVO
	 */
	@Override
	@Async("asyncExecutor")
	@Retryable(value = CustomException.class, maxAttemptsExpression = "${retryConfig.count.source}", backoff = @Backoff(delayExpression = "${retryConfig.backOffDelay.source}")) 
	public CompletableFuture<VendorResponse> invokeService(String emplId, String source, String url, TranVO tranVO) {
		log.info("Invoking DemoEO.invokeService() method");
		long startTime = System.currentTimeMillis();
		int statusCode = HttpStatus.OK.value();
		
		VendorResponse response = new VendorResponse();
		ResponseEntity<SourceResponse> responseEntity = null;
		try {
			responseEntity = template.postForEntity(url, emplId, SourceResponse.class);
			if(!ObjectUtils.isEmpty(responseEntity) && !ObjectUtils.isEmpty(responseEntity.getBody())) {
				response.setSource(source);
				response.setValid(responseEntity.getBody().isValid());
			}
		} catch (HttpStatusCodeException e) {
		    statusCode = e.getRawStatusCode();
			log.error("HttpStatusCodeException Error Processing Datasource : {}", e.getMessage());
			throw new CustomException(e);
		} catch (Exception e) {
		    statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
			log.error("Error Processing Datasource : {}", e.getMessage());
			throw new CustomException(e);
		} finally {
			helper.addServiceDetails(tranVO, source, helper.getResponseTime(startTime), statusCode);
		}
		return CompletableFuture.completedFuture(response);
	}
	
	@Recover
	private CompletableFuture<VendorResponse> invokeServiceFallback(CustomException e, String emplId, String source) {
		log.warn("Max retry attempt to call source has reached for employeeID : {} , source : {}", emplId, source);
		//throw new CustomException(e);
		VendorResponse response = new VendorResponse();
		response.setSource(source);
		response.setValid(false);
		return CompletableFuture.completedFuture(response);
	}
	

}
