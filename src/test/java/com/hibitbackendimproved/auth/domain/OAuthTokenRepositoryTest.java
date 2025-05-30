package com.hibitbackendimproved.auth.domain;

import com.hibitbackendimproved.IntegrationTestSupport;
import com.hibitbackendimproved.member.domain.Member;
import com.hibitbackendimproved.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hibitbackendimproved.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendimproved.common.fixtures.OAuthTokenFixtures.REFRESH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class OAuthTokenRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OAuthTokenRepository oAuthTokenRepository;

    @DisplayName("member id의 OAuthToken이 존재할 경우 true를 반환한다.")
    @Test
    void member_id의_OAuthToken이_존재할_경우_true를_반환한다() {
        // given
        Member 팬시 = memberRepository.save(팬시());
        oAuthTokenRepository.save(new OAuthToken(팬시, REFRESH_TOKEN));

        // when
        boolean actual = oAuthTokenRepository.existsByMemberId(팬시.getId());

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("member id의 OAuthToken이 존재하지 않을 경우 false를 반환한다.")
    @Test
    void member_id의_OAuthToken이_존재하지_않을_경우_false를_반환한다() {
        // given & when
        boolean actual = oAuthTokenRepository.existsByMemberId(0L);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("member id의 OAuthToken이 존재할 경우 Optional은 비어있지 않다.")
    @Test
    void member_id의_OAuthToken이_존재할_경우_Optional은_비어있지_않다() {
        // given
        Member 팬시 = memberRepository.save(팬시());
        oAuthTokenRepository.save(new OAuthToken(팬시, REFRESH_TOKEN));

        // when
        Optional<OAuthToken> actual = oAuthTokenRepository.findByMemberId(팬시.getId());

        // then
        assertThat(actual).isNotEmpty();
    }

    @DisplayName("member id의 OAuthToken이 존재하지 않을 경우 비어있다.")
    @Test
    void member_id의_OAuthToken이_존재하지_않을_경우_비어있다() {
        // given & when
        Optional<OAuthToken> actual = oAuthTokenRepository.findByMemberId(0L);

        // then
        assertThat(actual).isEmpty();
    }
}
