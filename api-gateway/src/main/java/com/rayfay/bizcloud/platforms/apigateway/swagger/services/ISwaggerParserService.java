package com.rayfay.bizcloud.platforms.apigateway.swagger.services;

import java.util.List;

import com.rayfay.bizcloud.platforms.apigateway.swagger.controller.SBaseRequest;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.ApiInfo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SearchApis;

/**
 * Created by stzhang on 2017/3/8.
 */
public interface ISwaggerParserService {
    /**
     * 根据Appname 同步应用的API
     * @param appName
     */
    public void synSwaggerDataFromAppServer(String appName);

    /**
     * 根据PathId 查询
     * pathId 格式： //{appName}/{path}
     * 例如： //appBasic/code/findAll
     * @param pathId
     * @return
     */
    public String queryApiDocs(String pathId, SBaseRequest sBaseRequest);

    /**
     * 根据text 检索Api接口
     * @param text
     * @return
     */
    public List<SearchApis> searchSwaggerApis(String text);

    public List<ApiInfo> searchSwaggerApisPageable(int pageSize, int pageNumber, String queryStr);

}
