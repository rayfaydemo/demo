package com.example;

import com.rayfay.bizcloud.core.commons.EnablePltWithMicroService;
import com.rayfay.bizcloud.core.commons.clientinfo.EnableFeignClientsWithClientInfo;
import com.rayfay.bizcloud.core.commons.displayed.DisplayApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * Created by HP on 2017/7/25.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import({DisplayApplicationConfiguration.class})
public class MicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }
}
