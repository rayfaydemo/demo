package com.rayfay.bizcloud.platforms.apigateway.test.swagger;

import com.rayfay.bizcloud.platforms.apigateway.swagger.controller.SBaseRequest;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SwaggerApplicationApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.SwaggerApisRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.SwaggerParserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by stzhang on 2017/3/8.
 */
public class ApisIntegrationTest extends AbstractTest {
    @Autowired
    protected SwaggerParserServiceImpl swaggerParserServiceImpl;

    @Autowired
    protected SwaggerApisRepository swaggerApisRepository;
    @Test
    public void  test1(){
        swaggerParserServiceImpl.synSwaggerDataFromAppServer("appbasic");
        SwaggerApplicationApis applicationApis = swaggerApisRepository.getApiByApiPath("//appbasic/category/findOne");
        assertNotNull(applicationApis);
        SBaseRequest sbr = new SBaseRequest("");
        String s = swaggerParserServiceImpl.queryApiDocs(applicationApis.getId(),sbr);
        assertNotNull(s);
        System.out.println("-------------------------");
        System.out.println(s);
    }

}
