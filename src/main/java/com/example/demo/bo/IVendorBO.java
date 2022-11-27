package com.example.demo.bo;

import com.example.demo.vo.request.ApiRequest;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.ApiResponse;

public interface IVendorBO {
	
	ApiResponse getSourceData(ApiRequest request, TranVO tranVO);

}
