package com.example.demo.controller;

import static com.example.demo.util.DemoConstant.CONTEXT_URL;
import static com.example.demo.util.DemoConstant.END_POINT_URL;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bo.IVendorBO;
import com.example.demo.util.Helper;
import com.example.demo.vo.request.ApiRequest;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = CONTEXT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class Controller {

	private final IVendorBO demoBO;
	private final Helper helper;

	/**
	 * Method implements endpoint for calling datasource
	 * @param apiRequest
	 * @return ApiResponse
	 */
	@PostMapping(value = END_POINT_URL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponse postMap(@RequestBody @Valid ApiRequest apiRequest) {
		log.info("Starting transaction");
		TranVO tranVO = helper.createTranVO();
		ApiResponse response = demoBO.getSourceData(apiRequest, tranVO);
		log.info("Transaction Completed with ResponseTime : {} and individual Service Response Time : {}",
				helper.getResponseTime(tranVO.getStartTime()), tranVO.getProcCodeAndTime());
		return response;
	}

}
