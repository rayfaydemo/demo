package com.rayfay.bizcloud.platforms.apigateway;

import com.rayfay.bizcloud.core.commons.EnablePltWithMicroService;
import com.rayfay.bizcloud.core.commons.feign.EnableFeignClientsWithJWTOAuth2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableFeignClientsWithJWTOAuth2
@EnablePltWithMicroService
public class MyApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyApiGatewayApplication.class, args);
	}
}
