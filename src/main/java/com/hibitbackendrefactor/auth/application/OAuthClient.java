package com.hibitbackendrefactor.auth.application;


import com.hibitbackendrefactor.auth.dto.OAuthMember;
import com.hibitbackendrefactor.auth.dto.response.OAuthAccessTokenResponse;

public interface OAuthClient {
    OAuthMember getOAuthMember(final String code, final String redirectUri);

    OAuthAccessTokenResponse getAccessToken(final String refreshToken);
}
