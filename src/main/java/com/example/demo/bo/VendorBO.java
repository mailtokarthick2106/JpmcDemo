package com.example.demo.bo;

import static com.example.demo.util.DemoConstant.NAME;
import static com.example.demo.util.DemoConstant.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.config.SourceConfig;
import com.example.demo.eo.IVendorEO;
import com.example.demo.util.Helper;
import com.example.demo.vo.request.ApiRequest;
import com.example.demo.vo.request.TranVO;
import com.example.demo.vo.response.ApiResponse;
import com.example.demo.vo.response.VendorResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class VendorBO implements IVendorBO {

	private final IVendorEO demoEO;
	private final SourceConfig sourceConfig;
	private final Helper helper;

	/**
	 * BO method to call datasource API
	 * @param request
	 * @param tranVO
	 * @return ApiResponse
	 */
	public ApiResponse getSourceData(ApiRequest request, TranVO tranVO) {
		log.info("Invoking DemoBO.getSourceData() method");
		ApiResponse resp = new ApiResponse();
		resp.setResult(getAsyncDatasourceResponse(request, tranVO));
		return resp;
	}

	/**
	 * Method to call Datasource url Asynchronously
	 * @param request
	 * @param tranVO
	 * @param configMapList
	 * @return List<VendorResponse>
	 */
	private List<VendorResponse> getAsyncDatasourceResponse(ApiRequest request, TranVO tranVO) {
		List<CompletableFuture<VendorResponse>> compList = new ArrayList<>();
		List<Map<String, String>> configMapList = sourceConfig.getSources();
		configMapList = !ObjectUtils.isEmpty(configMapList) ? configMapList : new ArrayList<>();
		for (String source : request.getSources()) {
			for (Map<String, String> configMap : configMapList) {
				if (source.equals(configMap.get(NAME))) {
					String url = configMap.get(URL);
					CompletableFuture<VendorResponse> compResponse = demoEO.invokeService(request.getEmployeeId(),
							source, url, tranVO);
					compList.add(compResponse);
					break;
				}
			}
		}
			
		return helper.joinAsync(compList);
	}

}
