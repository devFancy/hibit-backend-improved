package com.hibitbackendimproved.auth.application;


import com.hibitbackendimproved.auth.domain.AuthToken;

public interface TokenCreator {
    AuthToken createAuthToken(final Long memberId);

    AuthToken renewAuthToken(final String outRefreshToken);

    Long extractPayLoad(final String accessToken);
}
