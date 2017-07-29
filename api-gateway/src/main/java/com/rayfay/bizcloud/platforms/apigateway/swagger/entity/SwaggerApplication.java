package com.rayfay.bizcloud.platforms.apigateway.swagger.entity;

import javax.persistence.*;

/**
 * Created by stzhang on 2017/3/2.
 */
@Entity
@Table(name = "T_SWAGGER_APPLICATION")
public class SwaggerApplication {


    @Id
    private String id;

    @Column(nullable = false)
    private String appName;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String basePath;

    @Column(nullable = true)
    private String fileId;

    @Column(nullable = true)
    private String version;


    public String getId() {
        return id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        this.id = appName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
