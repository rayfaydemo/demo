package com.example;

import com.rayfay.bizcloud.core.commons.EnablePltWithMicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by HP on 2017/7/25.
 */
@SpringBootApplication
@EnablePltWithMicroService
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
