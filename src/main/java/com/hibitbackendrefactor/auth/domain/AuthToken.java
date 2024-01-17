package com.hibitbackendrefactor.auth.domain;

import com.hibitbackendrefactor.auth.exception.NotFoundTokenException;

public class AuthToken {

    private String accessToken;
    private String refreshToken;

    private int isProfileRegistered;

    public AuthToken(final String accessToken, final String refreshToken, final int isProfileRegistered) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isProfileRegistered = isProfileRegistered;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getIsProfileRegistered() {
        return isProfileRegistered;
    }

    public void validateHasSameRefreshToken(final String otherRefreshToken) {
        if (!refreshToken.equals(otherRefreshToken)) {
            throw new NotFoundTokenException("회원의 리프레시 토큰이 아닙니다.");
        }
    }
}
