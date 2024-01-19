package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.post.exception.NotFoundPostStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostStatusTest {
    @DisplayName("게시글 모집 상태를 가져온다.")
    @ParameterizedTest
    @EnumSource
    void 게시글_모집_상태를_가져온다(final PostStatus postStatus) {
        // given & when & then
        assertAll(() -> {
            assertThat(PostStatus.from(postStatus.name())).isEqualTo(postStatus);
            assertThat(PostStatus.from(postStatus.name().toLowerCase())).isEqualTo(postStatus);
        });
    }

    @DisplayName("존재하지 않는 모집상태인 경우 예외를 던진다.")
    @Test
    void 존재하지_않는_모집상태인_경우_예외를_던진다() {
        // given
        String notFoundPostStatus = "존재하지 않는 모집상태";

        //  when & then
        assertThatThrownBy(() -> PostStatus.from(notFoundPostStatus))
                .isInstanceOf(NotFoundPostStatusException.class);
     }
}