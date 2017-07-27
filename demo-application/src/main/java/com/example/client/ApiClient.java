package com.example.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by HP on 2017/7/26.
 */
@FeignClient(name = "API-GATEWAY/hosts/demo-application-service")
public interface ApiClient {

	@RequestMapping(method = { RequestMethod.GET }, value = "/employee/getPageable")
	JSONObject getEmployeePageable(@RequestParam(name = "pageSize", required = false) int pageSize,
						   @RequestParam(name = "pageNumber", required = false) int pageNumber,
						   @RequestParam(name = "name", required = false) String name);

}
