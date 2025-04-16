package com.hibitbackendimproved.auth.dto.response;

public class AccessAndRefreshTokenResponse {

    private final String accessToken;

    private final String refreshToken;

    private int isProfileRegistered;

    public AccessAndRefreshTokenResponse(final String accessToken, final String refreshToken
            , final int isProfileRegistered) {
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
}
