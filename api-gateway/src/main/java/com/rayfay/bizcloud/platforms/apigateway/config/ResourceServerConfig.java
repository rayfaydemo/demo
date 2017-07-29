package com.rayfay.bizcloud.platforms.apigateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by shenfu on 2017/5/4.
 */
@Configuration
@EnableResourceServer
//@Profile("cloud")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/v2/**").permitAll();
		http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
	}
}
