package com.example.demo.eo;

import java.util.concurrent.CompletableFuture;

import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.VendorResponse;

public interface IVendorEO {
	
	CompletableFuture<VendorResponse> invokeService(String emplId, String source, String url, TranVO tranVO);

}
