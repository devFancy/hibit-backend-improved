package com.hibitbackendrefactor.auth.application;


import com.hibitbackendrefactor.auth.domain.AuthAccessToken;
import com.hibitbackendrefactor.auth.domain.AuthToken;

public interface TokenCreator {
    AuthToken createAuthToken(final Long memberId);

    AuthAccessToken createAuthAccessToken(final Long memberId);

    AuthAccessToken renewAuthToken(final String outRefreshToken);

    Long extractPayLoad(final String accessToken);
}
