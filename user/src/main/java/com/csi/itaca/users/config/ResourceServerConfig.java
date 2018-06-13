package com.csi.itaca.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "itaca-application";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*
        *  here configure the security zone:
        *           the resource /oauth/token is public and not security
        *           the resource /user is private only for admin role
        *           any resources in the itaca are in secure zone and need use the token
        */
        http
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                .antMatchers("/oauth/token", "/oauth/authorize**", "/public").permitAll()
                .antMatchers("/user").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
                .and()
                .formLogin() // if exist form  login
                .permitAll()
                .and()
                .logout()    // if exist form or process logout
                .permitAll();
    }

}