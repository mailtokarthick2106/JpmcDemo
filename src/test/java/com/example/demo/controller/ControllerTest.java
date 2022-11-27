package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.bo.IVendorBO;
import com.example.demo.helper.CommonHelper;
import com.example.demo.util.DemoConstant;
import com.example.demo.util.Helper;
import com.example.demo.vo.request.ApiRequest;
import com.example.demo.vo.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ControllerTest extends CommonHelper {

	MockMvc mockMvc;
	
	@InjectMocks
	Controller controller;
	
	@Mock
	IVendorBO demoBO;
	
	@Spy
	Helper helper;
	
	@Captor
	ArgumentCaptor<ApiRequest> requestCaptor;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).addPlaceholderValue("root.context-path", DemoConstant.CONTEXT_URL).build();
	}

	@DisplayName("JUnit to test the vendor invoke method")
	@Test
	void givenApiRequestTranVO_whenGetSourceDataCalled_thenReturnValidResponse() throws Exception {
		when(demoBO.getSourceData(any(), any())).thenReturn(new ApiResponse());
		ObjectMapper mapper = new ObjectMapper();
		byte[] inputByteArray = mapper.writeValueAsBytes(getApiRequest());
		MvcResult response = mockMvc
				.perform(post(DemoConstant.CONTEXT_URL + DemoConstant.END_POINT_URL)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputByteArray))
				.andExpect(status().is2xxSuccessful()).andReturn();
		assertNotNull(response);
		verify(demoBO).getSourceData(requestCaptor.capture(), any());
		assertThat(getApiRequest()).isEqualTo(requestCaptor.getValue());
		
	}

}
