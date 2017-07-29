package com.rayfay.bizcloud.platforms.apigateway.test.swagger;

import com.rayfay.bizcloud.platforms.apigateway.swagger.controller.SBaseRequest;
import com.rayfay.bizcloud.platforms.apigateway.swagger.entity.SwaggerApplicationApis;
import com.rayfay.bizcloud.platforms.apigateway.swagger.mongo.SwaggerDocumentRepo;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.SwaggerApisRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.repository.SwaggerApplicationRepository;
import com.rayfay.bizcloud.platforms.apigateway.swagger.services.EurakeFounder;
import com.rayfay.bizcloud.platforms.apigateway.test.service.SwaggerParserServiceTestImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import static junit.framework.Assert.*;

/**
 * Created by stzhang on 2017/3/8.
 */
public class ApisReposotoryTests extends AbstractTest implements ApplicationContextAware {

    protected static SwaggerParserServiceTestImpl swaggerParserServiceTestImpl = null;

    protected static SwaggerApisRepository swaggerApisRepository = null;
    private static ApplicationContext applicationContext;

    @Before
    public void  setup(){
        if(swaggerParserServiceTestImpl == null) {
            swaggerParserServiceTestImpl = new SwaggerParserServiceTestImpl();
            swaggerApisRepository = applicationContext.getBean(SwaggerApisRepository.class);
            SwaggerApplicationRepository swaggerApplicationRepository = applicationContext.getBean(SwaggerApplicationRepository.class);
            EurakeFounder eurakeFounder = applicationContext.getBean(EurakeFounder.class);
            SwaggerDocumentRepo swaggerDocumentRepo = applicationContext.getBean(SwaggerDocumentRepo.class);
            swaggerParserServiceTestImpl.setApisRepository(swaggerApisRepository);
            swaggerParserServiceTestImpl.setApplicationRepository(swaggerApplicationRepository);
            swaggerParserServiceTestImpl.setDocumentRepo(swaggerDocumentRepo);
            swaggerParserServiceTestImpl.setFounder(eurakeFounder);

        }
    }



    @Test
    public void  test1(){
        swaggerParserServiceTestImpl.synSwaggerDataFromAppServer("appbasic");
        SwaggerApplicationApis applicationApis = swaggerApisRepository.getApiByApiPath("//appbasic/category/findOne");
        assertNotNull(applicationApis);
        SBaseRequest sbr = new SBaseRequest("");
        String s = swaggerParserServiceTestImpl.queryApiDocs(applicationApis.getId(), sbr);
        assertNotNull(s);
        System.out.println("-------------------------");
        System.out.println(s);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         this.applicationContext = applicationContext;
    }
}
