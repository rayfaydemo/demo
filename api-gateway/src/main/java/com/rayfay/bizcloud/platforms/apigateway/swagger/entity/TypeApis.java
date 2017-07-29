package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * 空间用户类
 */
@Entity
@IdClass(TypeApisPK.class)
@Table(name = "type_apis") //指定表名为users
public class TypeApis {

	@Id
    @Column(name = "typeId", nullable = false)
	private String typeId;
    
	@Id
    @Column(name = "apiPath",nullable = false)
    private String apiPath;
		
	public String getTypeId() {
		return typeId;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}



	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
