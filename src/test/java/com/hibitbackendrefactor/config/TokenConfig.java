package com.hibitbackendrefactor.config;

import com.hibitbackendrefactor.auth.application.StubTokenProvider;
import com.hibitbackendrefactor.auth.application.TokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.hibitbackendrefactor.common.AuthFixtures.더미_시크릿_키;

@TestConfiguration
public class TokenConfig {

    @Bean
    public TokenProvider tokenProvider() {
        return new StubTokenProvider(더미_시크릿_키);
    }
}
