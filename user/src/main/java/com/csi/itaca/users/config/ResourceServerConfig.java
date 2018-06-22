package com.csi.itaca.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.resource_id}")
    private String RESOURCE_ID;

    @Value("${security.enable-csrf}")
    private boolean csrfEnabled;

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
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //agregado para prueba
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
                .permitAll()
                .and().csrf().and().httpBasic().disable()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);

        if(!csrfEnabled)
        {
            http.csrf().disable();
        }

    }

}