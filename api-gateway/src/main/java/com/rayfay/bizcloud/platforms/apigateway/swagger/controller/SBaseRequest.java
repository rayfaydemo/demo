package com.rayfay.bizcloud.platforms.apigateway.swagger.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by stzhang on 2017/3/13.
 */
public class SBaseRequest {
    private String host = null;
    private String basePath = null;

    public SBaseRequest(String host){
        this.host = host;
    }

    public SBaseRequest(String host, String basePath){
         this.host = host;
         this.basePath = basePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    /**
     *
     * @param request
     * @return
     */
    public static SBaseRequest parse(HttpServletRequest request){
        String host = request.getServerName();
        int port = request.getServerPort();
        if(port == 80){
            return new SBaseRequest(host);
        }else {
            return new SBaseRequest(host+":"+port);
        }
    }
}
