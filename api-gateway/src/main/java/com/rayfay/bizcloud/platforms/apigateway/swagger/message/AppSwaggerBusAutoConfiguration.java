package com.rayfay.bizcloud.platforms.apigateway.swagger.message;

import com.rayfay.bizcloud.platforms.apigateway.swagger.services.ISwaggerParserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by stzhang on 2017/3/13.
 */
@Configuration
@EnableBinding(AppSwaggerSynClient.class)
public class AppSwaggerBusAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(AppSwaggerBusAutoConfiguration.class);

    @Autowired
    @Input(AppSwaggerSynClient.INPUT)
    private SubscribableChannel busInputChannel;


    @Autowired
    private ISwaggerParserService swaggerParserService;

    @StreamListener(AppSwaggerSynClient.INPUT)
    public void acceptRemote(String message) {
        if(StringUtils.isNotBlank(message) && message.startsWith("startup:")){
            String appName = message.substring(8);
            logger.info("received message {} ", message);
            logger.info("start to syn swagger metadata from app");
            swaggerParserService.synSwaggerDataFromAppServer(appName);
            logger.info("success to syn swagger metadata from app");
        }
    }
}
