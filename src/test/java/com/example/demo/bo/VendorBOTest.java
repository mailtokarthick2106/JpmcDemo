package com.example.demo.bo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.config.SourceConfig;
import com.example.demo.eo.VendorEO;
import com.example.demo.helper.CommonHelper;
import com.example.demo.util.Helper;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.ApiResponse;
import com.example.demo.vo.response.VendorResponse;

@ExtendWith(MockitoExtension.class)
class VendorBOTest extends CommonHelper{
	
	@InjectMocks
	VendorBO vendorBO;
	
	@Mock
	VendorEO demoEO;
	
	@Mock Helper helper;
	
	@Mock
	SourceConfig sourceConfig;
	
	@DisplayName("JUnit to test the vendor invoke method")
	@Test
	void givenApiRequestTranVO_whenGetSourceDataCalled_thenReturnValidResponse() {
		Mockito.when(sourceConfig.getSources()).thenReturn(sourceConfig());
		Mockito.when(demoEO.invokeService(anyString(), anyString(), anyString(), any(TranVO.class))).thenReturn(new CompletableFuture<VendorResponse>());
		Mockito.when(helper.joinAsync(any())).thenReturn(new ArrayList<>());
		ApiResponse response = vendorBO.getSourceData(getApiRequest(), getTranVO());
		assertNotNull(response);
		verify(sourceConfig, times(1)).getSources();
		verify(demoEO, times(2)).invokeService(anyString(), anyString(), anyString(), any(TranVO.class));
		
	}
	
	@DisplayName("JUnit to test the vendor invoke method with null source config")
	@Test
	void givenApiRequestTranVO_whenGetSourceDataCalledWithNullSourceConfig_thenReturnEmptyResponse() {
		Mockito.when(sourceConfig.getSources()).thenReturn(null);
		ApiResponse response = vendorBO.getSourceData(getApiRequest(), getTranVO());
		assertTrue(response.getResult().isEmpty());
		verify(sourceConfig, times(1)).getSources();
		verify(demoEO, never()).invokeService(anyString(), anyString(), anyString(), any(TranVO.class));
	}
	
	@DisplayName("JUnit to test the vendor invoke method with different source request other than in config property")
	@Test
	void givenApiRequestTranVO_whenGetSourceDataCalledWithDiffSource_thenReturnEmptyResponse() {
		Mockito.when(sourceConfig.getSources()).thenReturn(sourceConfig());
		ApiResponse response = vendorBO.getSourceData(getApiRequestWithDiffSource(), getTranVO());
		assertTrue(response.getResult().isEmpty());
		verify(sourceConfig, times(1)).getSources();
		verify(demoEO, never()).invokeService(anyString(), anyString(), anyString(), any(TranVO.class));
	}
	
	@DisplayName("JUnit to test the vendor invoke method with null source request")
	@Test
	void givenApiRequestTranVO_whenGetSourceDataCalledWithNullSourceRequest_thenReturnEmptyResponse() {
		Mockito.when(sourceConfig.getSources()).thenReturn(sourceConfig());
		ApiResponse response = vendorBO.getSourceData(getApiRequestWithNullSource(), getTranVO());
		assertTrue(response.getResult().isEmpty());
		verify(sourceConfig, times(1)).getSources();
		verify(demoEO, never()).invokeService(anyString(), anyString(), anyString(), any(TranVO.class));
	}

}
