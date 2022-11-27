package com.example.demo.eo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.exception.CustomException;
import com.example.demo.util.Helper;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.SourceResponse;
import com.example.demo.vo.response.VendorResponse;

@ExtendWith(MockitoExtension.class)
class VendorEOTest {
	
	@InjectMocks
	VendorEO vendorEO;
	
	@Spy
	Helper helper;
	
	@Mock
	RestTemplate template;
	
	TranVO tranVO;
	
	@BeforeEach
	void setUp() {
		tranVO = helper.createTranVO();
		
	}

	@DisplayName("Calling invokeService method")
	@Test
	void givenEmpIdSourceUrlTranVO_wheninvokeServiceCalled_returnCompletableFutureVendorResponse() throws InterruptedException, ExecutionException {
		ResponseEntity<SourceResponse> response = new ResponseEntity<>(HttpStatus.OK); 
		Mockito.when(template.postForEntity("dummyurl", "123", SourceResponse.class)).thenReturn(response);
		CompletableFuture<VendorResponse> responseEntity = vendorEO.invokeService("123", "source1", "dummyurl", tranVO);
		assertNotNull(responseEntity.get());
		assertEquals(HttpStatus.OK.value(), tranVO.getProcCodeAndTime().get("source1").getStatusCode());
		
	}
	
	@DisplayName("Calling invokeService method during Exception Scenario")
	@Test
	void givenEmpIdSourceUrlTranVO_wheninvokeServiceCalledDuringException_throwException() throws InterruptedException, ExecutionException {
		Mockito.when(template.postForEntity("dummyurl", "123", SourceResponse.class)).thenThrow(new RuntimeException());
		assertThrows(CustomException.class, ()-> vendorEO.invokeService("123", "source1", "dummyurl", tranVO));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), tranVO.getProcCodeAndTime().get("source1").getStatusCode());
		
	}
	
	@DisplayName("Calling invokeService method during Client Exception Scenario")
	@Test
	void givenEmpIdSourceUrlTranVO_wheninvokeServiceCalledDuringClientException_throwException() throws InterruptedException, ExecutionException {
		Mockito.when(template.postForEntity("dummyurl", "123", SourceResponse.class)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
		assertThrows(CustomException.class, ()-> vendorEO.invokeService("123", "source1", "dummyurl", tranVO));
		assertEquals(400, tranVO.getProcCodeAndTime().get("source1").getStatusCode());
		
	}
}
