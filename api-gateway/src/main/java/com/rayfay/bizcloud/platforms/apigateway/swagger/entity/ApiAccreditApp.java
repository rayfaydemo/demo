package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import javax.persistence.*;

/**
 * Created by zhangwei on 2017/7/12.
 */
@Entity
@Table(name = "T_API_ACCREDIT_APP")
public class ApiAccreditApp {
    @Id
    @GeneratedValue
    @Column(name = "id", length=20)
    private long id;

    @Column(nullable = false)
    private String apiTypeId;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientKey;

    @Column
    private String dummyAppId = "";

    @Column
    private String spaceId = "";

    @Column
    private String organizationId = "";

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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
