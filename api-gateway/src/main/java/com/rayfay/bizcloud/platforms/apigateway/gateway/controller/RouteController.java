package com.rayfay.bizcloud.platforms.apigateway.gateway.controller;

import com.google.common.collect.Lists;
import com.rayfay.bizcloud.platforms.apigateway.gateway.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by stzhang on 2017/3/14.
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteLocator routeLocator;

//    @Autowired
//    private ExtractClient extractClient;
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public List<RouteMapper> searchSwaggerApis(){
//        extractClient.findAllUnit();
        List<Route> routes = routeLocator.getRoutes();
        List<RouteMapper> routeMappers = Lists.newArrayList();
        if(routes != null){
            for (Route route : routes) {
                RouteMapper r = new RouteMapper();
                r.setRoute(route.getFullPath());
                r.setServiceId(route.getLocation());
                routeMappers.add(r);
            }
        }
        return routeMappers;
    }
}
