package com.hibitbackendimproved.auth.domain;

import com.hibitbackendimproved.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.hibitbackendimproved.common.fixtures.MemberFixtures.팬시;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OAuthTokenTest {
    @DisplayName("OAuth token을 생성한다.")
    @Test
    void OAuth_token을_생성한다() {
        // given
        Member 팬시 = 팬시();
        String refreshToken = "refreshToken";

        // when & then
        assertDoesNotThrow(() -> new OAuthToken(팬시, refreshToken));
    }
    @DisplayName("refresh token을 교체한다.")
    @Test
    void refresh_token을_교체한다() {
        // given
        Member 팬시 = 팬시();
        String refreshToken = "refreshToken";
        OAuthToken oAuthToken = new OAuthToken(팬시, refreshToken);

        String updatedRefreshToken = "updateRefreshToekn";

        // when
        oAuthToken.change(updatedRefreshToken);

        // then
        assertThat(oAuthToken.getRefreshToken()).isEqualTo(updatedRefreshToken);
    }
}
