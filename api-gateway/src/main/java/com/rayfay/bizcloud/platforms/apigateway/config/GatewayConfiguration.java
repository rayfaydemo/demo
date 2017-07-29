package com.rayfay.bizcloud.platforms.apigateway.config;

import com.rayfay.bizcloud.platforms.apigateway.gateway.MyServiceRouteMapper;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by stzhang on 2017/3/13.
 */

@Configuration
public class GatewayConfiguration {
    @Bean
    public ServiceRouteMapper serviceRouteMapper() throws Exception {
        return new MyServiceRouteMapper();
    }
}
