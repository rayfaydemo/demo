//package com.rayfay.bizcloud.platforms.apigateway.config;
//
//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.stereotype.Component;
//
//@Configuration
//@Component
//@EnableOAuth2Sso
//public class OAuth2SsoApplication extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().disable();
//        http.antMatcher("/**").authorizeRequests().anyRequest()
//                .authenticated().and().csrf().disable()
//                .logout().logoutUrl("/logout").permitAll()
//                .logoutSuccessUrl("/");
//    }
//}
