package com.hibitbackendrefactor.auth.application;


import com.hibitbackendrefactor.auth.domain.AuthToken;

public interface TokenCreator {
    AuthToken createAuthToken(final Long memberId);

    AuthToken renewAuthToken(final String outRefreshToken);

    Long extractPayLoad(final String accessToken);
}
