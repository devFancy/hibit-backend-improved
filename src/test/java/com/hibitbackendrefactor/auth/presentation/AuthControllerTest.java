package com.hibitbackendrefactor.auth.presentation;

import com.hibitbackendrefactor.ControllerTestSupport;
import com.hibitbackendrefactor.auth.exception.InvalidTokenException;
import com.hibitbackendrefactor.infrastructure.oauth.exception.OAuthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import javax.servlet.http.Cookie;

import static com.hibitbackendrefactor.common.AuthFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTestSupport {

    @DisplayName("OAuth 소셜 로그인을 위한 링크와 상태코드 200을 반환한다.")
    @Test
    void OAuth_소셜_로그인을_위한_링크와_상태코드_200을_반환한다() throws Exception {
        // given
        given(oAuthUri.generate(any())).willReturn(OAuth_로그인_링크);

        // when & then
        mockMvc.perform(get("/api/auth/{oauthProvider}/oauth-uri?redirectUri={redirectUri}", GOOGLE_PROVIDER,
                        "https://hibit.com/oauth"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("OAuth 구글 로그인을 하면 accessToken과 refreshToken 값과 상태코드 200을 반환한다.")
    @Test
    void OAuth_구글_로그인을_하면_accessToken과_refreshToken_값과_상태코드_200을_반환한다() throws Exception {
        // given
        given(authService.generateAccessAndRefreshToken(any())).willReturn(MEMBER_인증_코드_토큰_응답());

        // when & then
        mockMvc.perform(post("/api/auth/{oauthProvider}/token", OAUTH_PROVIDER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MEMBER_인증_코드_토큰_요청())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("OAuth 로그인 과정에서 Resource Server 에러가 발생하면 상태코드 500을 반환한다.")
    @Test
    void OAuth_로그인_과정에서_Resource_Server_에러가_발생하면_상태코드_500을_반환한다() throws Exception {
        // given
        given(authService.generateAccessAndRefreshToken(any())).willThrow(new OAuthException());

        // when & then
        mockMvc.perform(post("/api/auth/{oauthProvider}/token", OAUTH_PROVIDER)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MEMBER_인증_코드_토큰_요청())))
                .andDo(print())
                .andExpect(status().isInternalServerError());

    }

    @DisplayName("리프레시 토큰을 통해 새로운 엑세스 토큰을 발급하면 상태코드 200을 반환한다.")
    @Test
    void 리프레시_토큰을_통해_새로운_액세스_토큰을_발급하면_상태코드_200을_반환한다() throws Exception {
        // given
        given(authService.generateAccessToken(any())).willReturn(MEMBER_리뉴얼_토큰_응답());

        // when & then
        mockMvc.perform(post("/api/auth/token/access")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("refreshToken", "ccccc.bbbbb.aaaaa")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("본인의 리프레시 토큰이 아닌 값으로 새로운 액세스 토큰을 발급하면 상태코드 401을 반환한다.")
    @Test
    void 본인의_리프레시_토큰이_아닌_값으로_새로운_액세스_토큰을_발급하면_상태코드_401을_반환한다() throws Exception {
        // given
        given(authService.generateAccessToken(any())).willThrow(new InvalidTokenException());

        // when & then
        mockMvc.perform(post("/api/auth/token/access")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("refreshToken", "ccccc.bbbbb.aaaaa")))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}