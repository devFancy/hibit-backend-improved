package com.hibitbackendrefactor.infrastructure.oauth.uri;


import com.hibitbackendrefactor.auth.application.OAuthUri;

public class StubOAuthUri implements OAuthUri {

    @Override
    public String generate(final String redirectUri) {
        return "https://localhost:3000";
    }
}
