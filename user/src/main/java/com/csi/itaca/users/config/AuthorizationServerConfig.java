package com.csi.itaca.users.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.jwt.client_secret}")
    private String CLIENT_SECRET;
    @Value("${security.jwt.client_id}")
    private String CLIENT_ID;
    @Value("${security.jwt.grant_type_password}")
    private String GRANT_TYPE_PASSWORD;
    @Value("${security.jwt.grant_type_password}")
    private String AUTHORIZATION_CODE;
    @Value("${security.jwt.refresh_token}")
    private String REFRESH_TOKEN;
    @Value("${security.jwt.implicit}")
    private String IMPLICIT;
    @Value("${security.jwt.scope_read}")
    private String SCOPE_READ;
    @Value("${security.jwt.scope_write}")
    private String SCOPE_WRITE;
    @Value("${security.jwt.trust}")
    private String TRUST;
    @Value("${security.jwt.signin_key}")
    private String SIGNIN_KEY;
    @Value("${security.jwt.access_tokn_validity_seconds}")
    private int ACCESS_TOKEN_VALIDITY_SECONDS;
    @Value("${security.jwt.frefresh_tokn_validity_seconds}")
    private int FREFRESH_TOKEN_VALIDITY_SECONDS;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNIN_KEY);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer
                .inMemory()
                .withClient(CLIENT_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .authorities()
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
    }
}