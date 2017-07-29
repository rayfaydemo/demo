package com.rayfay.bizcloud.platforms.apigateway.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.util.StringUtils;

/**
 * Created by stzhang on 2017/3/13.
 */
public class MyServiceRouteMapper implements ServiceRouteMapper {

    private Logger logger = LoggerFactory.getLogger(MyServiceRouteMapper.class);

    @Override
    public String apply(String serviceId) {
        String route = "";
        if(StringUtils.hasText(serviceId)){
            route = "/hosts/"+ serviceId ;
        }
        route = cleanRoute(route);
        logger.info("apply routeMapper: serviceId={}  route={}", serviceId, route);
        return StringUtils.hasText(route) ? route : serviceId;
    }

    /**
     * clean route.
     * @param route
     * @return
     */
    private String cleanRoute(String route)
    {
        String routeToClean = route.replaceAll("/{2,}", "/");
        if (routeToClean.startsWith("/")) {
            routeToClean = routeToClean.substring(1);
        }
        if (routeToClean.endsWith("/")) {
            routeToClean = routeToClean.substring(0, routeToClean.length() - 1);
        }
        return routeToClean;
    }
}
