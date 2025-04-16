package com.hibitbackendimproved.config;

import com.hibitbackendimproved.auth.application.OAuthClient;
import com.hibitbackendimproved.auth.application.OAuthUri;
import com.hibitbackendimproved.infrastructure.oauth.client.StubOAuthClient;
import com.hibitbackendimproved.infrastructure.oauth.uri.StubOAuthUri;
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
