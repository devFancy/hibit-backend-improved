package com.hibitbackendrefactor.common;

import com.hibitbackendrefactor.auth.dto.request.TokenRenewalRequest;
import com.hibitbackendrefactor.auth.dto.request.TokenRequest;
import com.hibitbackendrefactor.auth.dto.response.AccessAndRefreshTokenResponse;

public class AuthFixtures {

    public static final String GOOGLE_PROVIDER = "google";
    public static final String OAUTH_PROVIDER = "oauthProvider";

    public static final String STUB_MEMBER_인증_코드 = "member authorization code";
    public static final String STUB_MEMBER_REFRESH_인증_코드 = "member refresh authorization code";
    public static final String STUB_CREATOR_인증_코드 = "creator authorization code";

    public static final String 더미_엑세스_토큰 = "aaaaa.bbbbb.ccccc";
    public static final String 더미_리프레시_토큰 = "ccccc.bbbbb.aaaaa";
    public static final String OAuth_로그인_링크 = "https://accounts.google.com/o/oauth2/v2/auth";
    public static final String MEMBER_이메일 = "member@email.com";

    public static final String 더미_시크릿_키 = "asdfasarspofjkosdfasdjkflikasndflkasndsdfjkadsnfkjasdn";

    public static final String STUB_OAUTH_ACCESS_TOKEN = "aaaaaaaaaa.bbbbbbbbbb.cccccccccc";

    public static TokenRequest MEMBER_인증_코드_토큰_요청() {
        return new TokenRequest(STUB_MEMBER_인증_코드, "https://dallog.me/oauth");
    }

    public static AccessAndRefreshTokenResponse MEMBER_인증_코드_토큰_응답() {
        return new AccessAndRefreshTokenResponse(1L, STUB_MEMBER_인증_코드, STUB_MEMBER_REFRESH_인증_코드);
    }

    public static TokenRenewalRequest MEMBER_리뉴얼_토큰_요청() {
        return new TokenRenewalRequest(더미_리프레시_토큰);
    }
}
