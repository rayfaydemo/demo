package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by stzhang on 2017/3/2.
 */
@Entity
@Table(name = "T_SWAGGER_APPLICATION_APIS")
public class SwaggerApplicationApis {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String swaggerAppId;

    @Column(nullable = false)
    private String apiPath;

    @Column(nullable = false)
    private String appName;

    @Column(nullable = true)
    @Convert(converter = TagsConverter.class)
    private List<String> tags;

    @Column(nullable = true)
    private String path;

    @Column(nullable = true)
    private String summary;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
        recaculateEntityApiPath();
    }

    public String getId() {
        return id;
    }

    public String getSwaggerAppId() {
        return swaggerAppId;
    }

    public void setSwaggerAppId(String swaggerAppId) {
        this.swaggerAppId = swaggerAppId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        recaculateEntityApiPath();
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        recaculateEntityApiPath();
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

    private void recaculateEntityApiPath(){
        if(this.path != null && this.path.startsWith("/")){
            this.apiPath = "//"+this.appName +  this.path + "/"+ this.method;
        }else {
            this.apiPath = "//"+this.appName + "/" + this.path + "/" + this.method;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiPath() {
        return apiPath;
    }

}
