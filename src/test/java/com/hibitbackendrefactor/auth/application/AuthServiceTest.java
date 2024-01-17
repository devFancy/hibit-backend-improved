package com.hibitbackendrefactor.auth.application;


import com.hibitbackendrefactor.auth.domain.TokenRepository;
import com.hibitbackendrefactor.auth.dto.request.TokenRenewalRequest;
import com.hibitbackendrefactor.auth.dto.response.AccessAndRefreshTokenResponse;
import com.hibitbackendrefactor.auth.dto.response.AccessTokenResponse;
import com.hibitbackendrefactor.auth.event.MemberSavedEvent;
import com.hibitbackendrefactor.auth.exception.InvalidTokenException;
import com.hibitbackendrefactor.config.ExternalApiConfig;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.List;

import static com.hibitbackendrefactor.common.OAuthFixtures.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(classes = ExternalApiConfig.class)
@ActiveProfiles("test")
@RecordApplicationEvents
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ApplicationEvents events;

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @DisplayName("토큰 생성을 하면 OAuth 서버에서 인증 후 토큰을 반환한다")
    @Test
    void 토큰_생성을_하면_OAuth_서버에서_인증_후_토큰들을_반환한다() {
        // given & when
        AccessAndRefreshTokenResponse actual = authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());

        // then
        assertAll(() -> {
            assertThat(actual.getAccessToken()).isNotEmpty();
            assertThat(actual.getRefreshToken()).isNotEmpty();
            assertThat(events.stream(MemberSavedEvent.class).count()).isEqualTo(1);
        });
    }
    @DisplayName("이미 가입된 회원에 대한 Authorization Code를 전달받으면 추가로 회원이 생성되지 않는다")
    @Test
    void 이미_가입된_회원에_대한_Authorization_Code를_전달받으면_추가로_회원이_생성되지_않는다() {
        // 이미 가입된 회원이 소셜 로그인 버튼을 클릭했을 경우엔 회원가입 과정이 생략되고, 곧바로 access token이 발급되어야 한다.

        // given
        authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());

        // when
        authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());
        List<Member> actual = memberRepository.findAll();

        // then
        assertThat(actual).hasSize(1);
    }

    @DisplayName("이미 가입된 회원이고 저장된 RefreshToken이 있으면, 저장된 RefreshToken을 반환한다.")
    @Test
    void 이미_가입된_회원이고_저장된_RefreshToken이_있으면_저장된_RefreshToken을_반환한다() {
        // 이미 가입된 회원이 소셜 로그인 버튼을 클릭했을 경우엔 회원가입 과정이 생략되고, 곧바로 access token과 refreshtoken이 발급되어야 한다.

        // given
        AccessAndRefreshTokenResponse response = authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());

        // when
        AccessAndRefreshTokenResponse actual = authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());

        // then
        assertThat(actual.getRefreshToken()).isEqualTo(response.getRefreshToken());
    }

    @DisplayName("리프레시 토큰으로 새로운 엑세스 토큰을 발급한다.")
    @Test
    void 리프레시_토큰으로_새로운_엑세스_토큰을_발급한다() {
        // given
        AccessAndRefreshTokenResponse response = authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());
        TokenRenewalRequest tokenRenewalRequest = new TokenRenewalRequest(response.getRefreshToken());

        // when
        AccessTokenResponse accessTokenResponse = authService.generateAccessToken(tokenRenewalRequest);

        // then
        assertThat(accessTokenResponse.getAccessToken()).isNotEmpty();
    }
    @DisplayName("리프레시 토큰으로 새로운 엑세스 토큰을 발급 할 때, 리프레시 토큰이 존재하지 않으면 예외를 던진다.")
    @Test
    void 리프레시_토큰으로_새로운_엑세스_토큰을_발급_할_때_리프레시_토큰이_존재하지_않으면_예외를_던진다() {
        // given
        authService.generateAccessAndRefreshToken(MEMBER.getOAuthMember());
        TokenRenewalRequest tokenRenewalRequest = new TokenRenewalRequest("DummyRefreshToken");

        // when & then
        assertThatThrownBy(() -> authService.generateAccessToken(tokenRenewalRequest))
                .isInstanceOf(InvalidTokenException.class);
    }
}
