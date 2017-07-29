package com.rayfay.bizcloud.platforms.apigateway.swagger.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by stzhang on 2017/3/10.
 */
@Document(collection="swagger_api_full_text", language = UsingTextIndexLanguages.usingLanguageName)
public class ApiFullText {

    @Id
    private String id;
    @TextIndexed
    private String apiPath;
    @TextIndexed
    private String appName;
    @TextIndexed
    private String tags;
    @TextIndexed
    private String path;
    @TextIndexed
    private String summary;
    @TextIndexed
    private String description;

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
}
