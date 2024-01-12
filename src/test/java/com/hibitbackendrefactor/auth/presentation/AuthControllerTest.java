package com.hibitbackendrefactor.auth.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibitbackendrefactor.auth.application.AuthService;
import com.hibitbackendrefactor.auth.application.OAuthUri;
import com.hibitbackendrefactor.config.ExternalApiConfig;
import com.hibitbackendrefactor.member.application.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.hibitbackendrefactor.common.AuthFixtures.GOOGLE_PROVIDER;
import static com.hibitbackendrefactor.common.AuthFixtures.OAuth_로그인_링크;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@Import(ExternalApiConfig.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected OAuthUri oAuthUri;

    @MockBean
    protected MemberService memberService;

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
}