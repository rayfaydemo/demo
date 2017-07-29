package com.rayfay.bizcloud.platforms.apigateway.swagger.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by stzhang on 2017/3/13.
 */
public interface AppSwaggerSynClient {

    String INPUT = "_springAppStarted";

    @Input(AppSwaggerSynClient.INPUT)
    SubscribableChannel appBusOutput();

}
