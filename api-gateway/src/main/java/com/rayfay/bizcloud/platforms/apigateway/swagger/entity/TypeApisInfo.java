package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

/**
 * 应用用户类
 */
public class TypeApisInfo {

    private String typeId;

    private String apiId;

    private String swaggerAppId;

    private String apiPath;

    private String appName;

    private String tags;

    private String path;

    private String summary;

    private String description;
    
    private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getSwaggerAppId() {
		return swaggerAppId;
	}

	public void setSwaggerAppId(String swaggerAppId) {
		this.swaggerAppId = swaggerAppId;
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
    
}