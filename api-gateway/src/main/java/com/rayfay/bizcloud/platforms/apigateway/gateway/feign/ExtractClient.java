package com.rayfay.bizcloud.platforms.apigateway.gateway.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "http://app-basic")
public interface ExtractClient {
	@RequestMapping(value = "/category/findAll", method = {RequestMethod.GET})
    void findAllUnit();
}
