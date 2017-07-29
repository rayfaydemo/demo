package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by stzhang on 2017/3/17.
 */
public class SearchApis {

    private String id;
    private String apiPath;
    private String appName;
    private String tags;
    private String path;
    private String summary;
    private String description;
    private int appCnt;

    public int getAppCnt() {
        return appCnt;
    }

    public void setAppCnt(int appCnt) {
        this.appCnt = appCnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static SearchApis convertSwaggerApplicationApi2(SwaggerApplicationApis api){
        SearchApis searchApi = new SearchApis();
        searchApi.setId(api.getId());
        searchApi.setAppName(api.getAppName());
        searchApi.setDescription(api.getDescription());
        searchApi.setPath(api.getPath());
        searchApi.setTags(StringUtils.join(api.getTags(), ","));
        searchApi.setSummary(api.getSummary());
        searchApi.setApiPath(api.getApiPath());
        return searchApi;
    }
}
