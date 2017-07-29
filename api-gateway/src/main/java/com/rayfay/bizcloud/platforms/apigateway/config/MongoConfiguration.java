package com.rayfay.bizcloud.platforms.apigateway.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

/**
 * Created by stzhang on 2017/3/2.
 */
@Configuration
public class MongoConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        Map<String, MongoDbFactory> factory =  applicationContext.getBeansOfType(MongoDbFactory.class);
        if(factory != null && !factory.isEmpty()){
            MongoDbFactory f =  factory.values().iterator().next();
            return new MongoTemplate(f);
        }
        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}