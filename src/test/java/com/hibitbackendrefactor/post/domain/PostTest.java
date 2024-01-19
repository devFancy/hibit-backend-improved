package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.post.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PostTest {

    @DisplayName("게시글을 등록한다.")
    @Test
    void 게시글을_등록한다() {
        // given & when & then
        assertDoesNotThrow(() -> 프로젝트_해시테크());
    }

    @DisplayName("게시글 제목이 null 이거나 공백인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 게시글_제목이_null_이거나_공백인_경우_예외를_던진다(String title) {
        // given & when & then
        assertThatThrownBy(() -> new Post(팬시(), title, 게시글내용1, 전시회제목1, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1))
                .isInstanceOf(InvalidTitleException.class);
    }

    @DisplayName("게시글 제목이 30을 초과한 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일", "예술전시회기반매칭서비스 예술전시회기반매칭서비스 예술전시회기반매칭서비스"})
    void 게시글_제목이_30을_초과한_경우_예외를_던진다(final String title) {
        // given & when & then
        assertThatThrownBy(() -> new Post(팬시(), title, 게시글내용1, 전시회제목1, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1))
                .isInstanceOf(InvalidTitleException.class);
    }

    @DisplayName("게시글 내용이 null 이거나 공백인 경우 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 게시글_내용이_null_이거나_공백인_경우_예외를_던진다(final String content) {
        // given & when & then
        assertThatThrownBy(() -> new Post(팬시(), 게시글제목1, content, 전시회제목1, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1))
                .isInstanceOf(InvalidContentException.class);
    }

    @DisplayName("게시글 내용이 200자 초과한 경우 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"오스틴 리 전시회 보러가실 분"
            + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분"
            + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분"
            + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분"
            + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분"
            + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분" + "오스틴 리 전시회 보러가실 분"})
    void 게시글_내용이_200자_초과한_경우_예외를_던진다(final String content) {
        // given & when & then
        assertThatThrownBy(() -> new Post(팬시(), 게시글제목1, content, 전시회제목1, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1))
                .isInstanceOf(InvalidContentException.class);
    }

    @DisplayName("전시회 제목이 null 이거나 공백이면 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 전시회_제목이_null_이거나_공백이면_예외를_던진다(String exhibition) {
        // given & when & then
        assertThatThrownBy(() -> new Post(팬시(), 게시글제목1, 게시글내용1, exhibition, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1))
                .isInstanceOf(InvalidExhibitionException.class);
    }

    @DisplayName("전시회 제목이 50을 초과한 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"일이삼사오육칠팔구십 일이삼사오육칠팔구십 일이삼사오육칠팔구십 일이삼사오육칠팔구십 일이삼사오육칠팔구십 일", "123456789012345678901234567890123456789012345678901"})
    void 전시회_제목이_50을_초과한_경우_예외를_던진다(final String exhibition) {
        // when & then
        assertThatThrownBy(() -> new Post(팬시(), 게시글제목1, 게시글내용1, exhibition, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1))
                .isInstanceOf(InvalidExhibitionException.class);

    }
}