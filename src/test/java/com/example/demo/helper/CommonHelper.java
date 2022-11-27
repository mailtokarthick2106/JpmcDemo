package com.example.demo.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.vo.request.ApiRequest;
import com.example.demo.vo.request.TranVO;

public class CommonHelper {
	
	public List<Map<String, String>> sourceConfig(){
		Map<String, String> map1 = new HashMap<>();
		map1.put("name", "source1");
		map1.put("url", "dummuurl");
		
		Map<String, String> map2 = new HashMap<>();
		map2.put("name", "source2");
		map2.put("url", "dummuurl");
		
		List<Map<String, String>> listMap = new ArrayList<>();
		listMap.add(map1);
		listMap.add(map2);
		
		return listMap;
		
		
	}
	
	
	public ApiRequest getApiRequest() {
		ApiRequest request = new ApiRequest();
		request.setEmployeeId("1234");
		List<String> sourceList = new ArrayList<>();
		sourceList.add("source1");
		sourceList.add("source2");
		request.setSources(sourceList);
		return request;
	}
	
	public ApiRequest getApiRequestWithDiffSource() {
		ApiRequest request = new ApiRequest();
		request.setEmployeeId("1234");
		List<String> sourceList = new ArrayList<>();
		sourceList.add("source3");
		sourceList.add("source4");
		request.setSources(sourceList);
		return request;
	}
	
	public ApiRequest getApiRequestWithNullSource() {
		ApiRequest request = new ApiRequest();
		request.setEmployeeId("1234");
		List<String> sourceList = new ArrayList<>();
		request.setSources(sourceList);
		return request;
	}
	
	
	
	public TranVO getTranVO() {
		TranVO tranVO = new TranVO();
		tranVO.setStartTime(System.currentTimeMillis());
		tranVO.setProcCodeAndTime(new ConcurrentHashMap<>());
		return tranVO;
	}

}
