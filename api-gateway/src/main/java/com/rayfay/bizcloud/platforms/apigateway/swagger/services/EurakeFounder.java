package com.rayfay.bizcloud.platforms.apigateway.swagger.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by stzhang on 2017/3/2.
 */
@Component
public class EurakeFounder {

    private Logger logger = LoggerFactory.getLogger(EurakeFounder.class);
    @Autowired
    private LoadBalancerClient loadBalancer;

    public String find(String appName) {
        ServiceInstance serviceInstance = loadBalancer.choose(appName);
        URI uri = null;
        try {
            uri = new URI("/v2/api-docs");
        } catch (URISyntaxException e) {
            logger.error("", e);
        }
        if (uri != null) {
            if (serviceInstance != null) {
                URI reconstructedUri = loadBalancer.reconstructURI(serviceInstance, uri);
                int port = reconstructedUri.getPort();
                String serviceUrl = reconstructedUri.toString();
                if (port == 80) {
                    serviceUrl = serviceUrl.replaceAll(":80", "");
                }
                return serviceUrl;
            }
        }
        return null;
    }



}
