package com.rayfay.bizcloud.platforms.apigateway.test.service;

import com.rayfay.bizcloud.platforms.apigateway.swagger.services.SwaggerParserServiceImpl;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

/**
 * Created by stzhang on 2017/3/8.
 */
public class SwaggerParserServiceTestImpl extends SwaggerParserServiceImpl {
    @Override
    protected Swagger swaggerParser(String url) {
        SwaggerParser parser = new SwaggerParser();
        Swagger swagger = parser.parse(swaggerJsonData());
        return swagger;
    }

    public String swaggerJsonData(){
        return "{\"swagger\":\"2.0\",\"info\":{\"description\":\"应用中心 应用基本信息 APIs\",\"version\":\"1.0\",\"title\":\"应用中心 应用基本信息 APIs\",\"contact\":{},\"license\":{}},\"host\":\"localhost:8080\",\"basePath\":\"/\",\"tags\":[{\"name\":\"app-category-controller\",\"description\":\"App Category Controller\"}],\"paths\":{\"/category/findAll\":{\"get\":{\"tags\":[\"app-category-controller\"],\"summary\":\"获取应用分类列表\",\"operationId\":\"findAllCodeUsingGET\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/AppCategory\"}}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/category/findOne\":{\"get\":{\"tags\":[\"app-category-controller\"],\"summary\":\"查找分类信息\",\"description\":\"根据ID查找分类信息\",\"operationId\":\"findOneUsingGET\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"in\":\"body\",\"name\":\"id\",\"description\":\"分类ID\",\"required\":true,\"schema\":{\"$ref\":\"#/definitions/\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/AppCategory\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}},\"/category/saveOne\":{\"post\":{\"tags\":[\"app-category-controller\"],\"summary\":\"saveOne\",\"operationId\":\"saveOneUsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"id\",\"in\":\"query\",\"description\":\"id\",\"required\":true,\"type\":\"integer\",\"format\":\"int64\"},{\"name\":\"categoryName\",\"in\":\"query\",\"description\":\"categoryName\",\"required\":true,\"type\":\"string\"},{\"name\":\"desc\",\"in\":\"query\",\"description\":\"desc\",\"required\":true,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/JsonResult\"}},\"201\":{\"description\":\"Created\"},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}}}}},\"definitions\":{\"JsonResult\":{\"properties\":{\"message\":{\"type\":\"string\"},\"success\":{\"type\":\"boolean\"}}},\"AppCategory\":{\"properties\":{\"categoryDesc\":{\"type\":\"string\"},\"categoryName\":{\"type\":\"string\"},\"id\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}";
    }





}
