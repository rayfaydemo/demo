package com.rayfay.bizcloud.platforms.apigateway.test.swagger;

import com.rayfay.bizcloud.platforms.apigateway.MyApiGatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by stzhang on 2017/3/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyApiGatewayApplication.class)
@WebIntegrationTest
@ActiveProfiles("dev")
public class AbstractTest {


}
