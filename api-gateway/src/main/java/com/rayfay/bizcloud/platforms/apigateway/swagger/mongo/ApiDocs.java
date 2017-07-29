package com.rayfay.bizcloud.platforms.apigateway.swagger.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by stzhang on 2017/3/2.
 */
@Document(collection="swagger_api_docs")
public class ApiDocs {
    @Id
    private String fileId;

    private String jsonData;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
