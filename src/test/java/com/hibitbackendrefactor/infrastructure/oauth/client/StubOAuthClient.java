package com.hibitbackendrefactor.infrastructure.oauth.client;


import com.hibitbackendrefactor.auth.application.OAuthClient;
import com.hibitbackendrefactor.auth.dto.OAuthMember;
import com.hibitbackendrefactor.auth.dto.response.OAuthAccessTokenResponse;
import com.hibitbackendrefactor.common.OAuthFixtures;

import static com.hibitbackendrefactor.common.AuthFixtures.STUB_OAUTH_ACCESS_TOKEN;

public class StubOAuthClient implements OAuthClient {

    @Override
    public OAuthMember getOAuthMember(final String code, final String redirectUri) {
        return OAuthFixtures.parseOAuthMember(code);
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(final String refreshToken) {
        return new OAuthAccessTokenResponse(STUB_OAUTH_ACCESS_TOKEN);
    }
}