package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * 应用实例类
 */
@Entity
public class ApiType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id",insertable = true, updatable = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String typeName;
    
    
    @Column(nullable = false,length=2048)
    private String description;
    
    
//	@OneToMany(fetch = FetchType.LAZY, targetEntity = TypeApis.class)
//	@JoinColumn(name = "typeId",insertable = false, updatable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
//	private List<TypeApis> typeApis = new ArrayList<>();
//	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

//	public List<TypeApis> getTypeApis() {
//		return typeApis;
//	}
//
//	public void setTypeApis(List<TypeApis> typeApis) {
//		this.typeApis = typeApis;
//	}

	
}
