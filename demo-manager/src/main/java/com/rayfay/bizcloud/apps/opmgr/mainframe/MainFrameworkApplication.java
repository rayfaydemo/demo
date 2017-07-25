package com.rayfay.bizcloud.apps.opmgr.mainframe;

import com.rayfay.bizcloud.core.commons.feign.EnableFeignClientsWithJWTOAuth2;
import com.rayfay.bizcloud.platforms.ui.annotation.EnableWebServerWithReact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableWebServerWithReact
@EnableFeignClientsWithJWTOAuth2
public class MainFrameworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainFrameworkApplication.class, args);
	}
}
