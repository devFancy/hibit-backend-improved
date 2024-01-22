package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PostTest {

    @DisplayName("게시글을 등록한다. - Case 1 ")
    @Test
    void 게시글을_등록한다_Case1() {
        // when & then
        Member 팬시 = 팬시();

        // when & then
        assertDoesNotThrow(() -> 프로젝트_해시테크(팬시));
    }

    @DisplayName("게시글을 등록한다. - Case 2")
    @Test
    void 게시글을_등록한다_Case2() {
        // when & then
        Member 팬시 = 팬시();

        // when & then
        assertDoesNotThrow(() -> new Post(팬시, 게시글제목1, 게시글내용1
                , 전시회제목1, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1
                , 함께하고싶은활동1, 게시글이미지1, 모집상태1));
    }

    @DisplayName("게시글을 작성한 회원 정보를 가져온다.")
    @Test
    void 게시글을_작성한_회원_정보를_가져온다() {
        // given
        Member 팬시 = 팬시();
        Post post = 오스틴리_전시회(팬시);

        // when
        Member foundMember = post.getMember();

        // then
        assertThat(foundMember).isEqualTo(팬시);
     }

     @DisplayName("새로 생성한 게시글에서 가져온 정보가 일치하는지 확인한다.")
     @Test
     void 새로_생성한_게시글에서_가져온_정보가_일치하는지_확인한다() {
         // given
         Member 팬시 = 팬시();
         Post post = 오스틴리_전시회(팬시);

         // when & then
         assertAll(
                 () -> assertThat(post.getTitle()).isEqualTo(게시글제목2),
                 () -> assertThat(post.getContent()).isEqualTo(게시글내용2),
                 () -> assertThat(post.getExhibition()).isEqualTo(전시회제목2),
                 () -> assertThat(post.getExhibitionAttendance()).isEqualTo(전시관람인원2),
                 () -> assertThat(post.getPossibleTime()).isEqualTo(전시관람희망날짜2),
                 () -> assertThat(post.getOpenChatUrl()).isEqualTo(오픈채팅방Url2),
                 () -> assertThat(post.getTogetherActivity()).isEqualTo(함께하고싶은활동2),
                 () -> assertThat(post.getPostStatus()).isEqualTo(모집상태2),
                 () -> assertThat(post.getImageName()).isEqualTo(게시글이미지2)
         );
      }
}