package com.hibitbackendrefactor.config;

import com.hibitbackendrefactor.auth.application.OAuthClient;
import com.hibitbackendrefactor.auth.application.OAuthUri;
import com.hibitbackendrefactor.infrastructure.oauth.client.StubOAuthClient;
import com.hibitbackendrefactor.infrastructure.oauth.uri.StubOAuthUri;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExternalApiConfig {

    @Bean
    public OAuthClient oAuthClient() {
        return new StubOAuthClient();
    }


    @Bean
    public OAuthUri oAuthUri() {
        return new StubOAuthUri();
    }
}
