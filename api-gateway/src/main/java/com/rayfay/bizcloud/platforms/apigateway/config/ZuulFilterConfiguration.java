package com.rayfay.bizcloud.platforms.apigateway.config;

import com.rayfay.bizcloud.platforms.apigateway.gateway.ClientInfoFilter;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ApiAccreditAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenfu on 2017/7/13.
 */
@Configuration
public class ZuulFilterConfiguration {
    private final ApiAccreditAppService apiAccreditAppService;

    @Autowired
    public ZuulFilterConfiguration(ApiAccreditAppService apiAccreditAppService) {
        this.apiAccreditAppService = apiAccreditAppService;
    }

    @Bean
    public ClientInfoFilter clientInfoFilter() {
        return new ClientInfoFilter(apiAccreditAppService);
    }
}
