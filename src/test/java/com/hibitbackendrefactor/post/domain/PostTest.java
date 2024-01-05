package com.hibitbackendrefactor.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
class PostTest {

    @DisplayName("게시글을 등록한다.")
    @Test
    void createPost() {
        // given & when & then
        assertDoesNotThrow(() -> Post.builder()
                .member(팬시())
                .title(게시글제목)
                .content(게시글내용)
                .exhibition(전시회제목)
                .exhibitionAttendance(전시관람인원)
                .possibleTimes(전시관람희망날짜)
                .openChatUrl(오픈채팅방Url)
                .togetherActivity(함께하고싶은활동)
                .postImages(게시글이미지들)
                .postStatus(모집상태)
                .build());
     }
}