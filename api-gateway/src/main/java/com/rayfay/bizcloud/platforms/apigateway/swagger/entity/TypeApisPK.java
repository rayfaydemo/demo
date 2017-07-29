package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import java.io.Serializable;


/**
 * 应用用户类
 */

public class TypeApisPK implements Serializable{

	private String typeId;
	
    private String apiPath;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}




    
   


	
}