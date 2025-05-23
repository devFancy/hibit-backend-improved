package com.hibitbackendimproved.auth.dto.response;

public class OAuthAccessTokenResponse {
    private String accessToken;

    private OAuthAccessTokenResponse() {
    }

    public OAuthAccessTokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
