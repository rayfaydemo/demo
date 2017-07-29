package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

/**
 * Created by zhangwei on 2017/7/12.
 */
public class AppApiTypeInfo {

    private String apiTypeId;

    private String dummyAppId;

    private String apiTypeName;

    private String apiTypeDesc;

    public String getApiTypeId() {
        return apiTypeId;
    }

    public void setApiTypeId(String apiTypeId) {
        this.apiTypeId = apiTypeId;
    }

    public String getDummyAppId() {
        return dummyAppId;
    }

    public void setDummyAppId(String dummyAppId) {
        this.dummyAppId = dummyAppId;
    }

    public String getApiTypeName() {
        return apiTypeName;
    }

    public void setApiTypeName(String apiTypeName) {
        this.apiTypeName = apiTypeName;
    }

    public String getApiTypeDesc() {
        return apiTypeDesc;
    }

    public void setApiTypeDesc(String apiTypeDesc) {
        this.apiTypeDesc = apiTypeDesc;
    }
}
