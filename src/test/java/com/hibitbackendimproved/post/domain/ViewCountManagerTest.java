package com.hibitbackendimproved.post.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ViewCountManagerTest {

    private ViewCountManager viewCountManager = new ViewCountManager();

    @DisplayName("오늘 방문한 게시글인지 확인한다. 방문했으면 true, 그렇지 않으면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("argsOfIsFirstAccess")
        //isFirstAccess
    void 오늘_방문한_게시글인지_확인한다_방문했으면_true_그렇지_않으면_false를_반환한다(final String logs, Long postId, boolean expected) {
        // given
        boolean result = viewCountManager.isFirstAccess(logs, postId);

        // when & then
        assertThat(result).isEqualTo(expected);
    }

    // <DATE>:1/2/3&
    static Stream<Arguments> argsOfIsFirstAccess() {
        int today = LocalDateTime.now().getDayOfMonth();
        int yesterday = today - 1;
        return Stream.of(
                Arguments.of("", 1L, true),
                Arguments.of(today + ":1/2/3", 4L, true),
                Arguments.of(today + ":1/2/3", 3L, false),
                Arguments.of(yesterday + ":1/2/3", 3L, true),
                Arguments.of(yesterday + ":1/2/3&" + today + ":1/2", 3L, true),
                Arguments.of(yesterday + ":1/2/3&" + today + ":1/2/3", 3L, false)
        );
    }

    @DisplayName("쿠키에 방문한 post에 대한 log를 추가한다.")
    @ParameterizedTest
    @MethodSource("argsOfAddAccessLogToCookie")
    void 쿠키에_방문한_post에_대한_log_를_추가한다(final String log, Long postId, String expectedUpdatedLog) {
        // given
        String updatedLog = viewCountManager.getUpdatedLog(log, postId);

        // when & then
        Assertions.assertThat(updatedLog).isEqualTo(expectedUpdatedLog);
    }

    private static Stream<Arguments> argsOfAddAccessLogToCookie() {
        int today = LocalDateTime.now().getDayOfMonth();
        int yesterday = today - 1;
        return Stream.of(
                Arguments.of("", 1L, today + ":1"),
                Arguments.of(yesterday + ":1/2/3", 1L,  today + ":1"),
                Arguments.of(today + ":1/2/3", 4L, today + ":1/2/3/4"),
                Arguments.of(yesterday + ":1/2/3&" + today + ":1/2", 3L, today + ":1/2/3")
        );
    }

}
