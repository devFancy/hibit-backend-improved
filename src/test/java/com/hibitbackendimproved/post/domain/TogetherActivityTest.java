package com.hibitbackendimproved.post.domain;

import com.hibitbackendimproved.post.exception.NotFoundTogetherActivityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TogetherActivityTest {
    @DisplayName("게시글에서 함께하고 싶은 활동을 가져온다.")
    @ParameterizedTest
    @EnumSource
    void 함께하고_싶은_활동를_가져온다(final TogetherActivity togetherActivity) {
        // given & when & then
        assertAll(() -> {
            assertThat(TogetherActivity.from(togetherActivity.name())).isEqualTo(togetherActivity);
            assertThat(TogetherActivity.from(togetherActivity.name().toLowerCase())).isEqualTo(togetherActivity);

        });

     }

    @DisplayName("존재하지 않는 함께하고싶은 활동인 경우 예외를 던진다.")
    @Test
    void 존재하지_않는_함께하고싶은_활동인_경우_예외를_던진다() {
        // given
        String notFoundTogetherActivity = "존재하지 않는 함께하기 활동입니다.";

        //  when & then
        assertThatThrownBy(() -> TogetherActivity.from(notFoundTogetherActivity))
                .isInstanceOf(NotFoundTogetherActivityException.class);
    }
}
