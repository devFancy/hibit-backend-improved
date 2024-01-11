package com.hibitbackendrefactor.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibitbackendrefactor.auth.application.AuthService;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.post.application.PostService;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.PostDetailResponse;
import com.hibitbackendrefactor.post.dto.response.PostResponse;
import com.hibitbackendrefactor.post.dto.response.PostsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static com.hibitbackendrefactor.post.dto.response.PostResponse.AttendanceAndTogetherActivity;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = PostController.class)
class PostControllerTest {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private AuthService authService;

    @DisplayName("게시글을 등록한다.")
    @Test
    void 게시글을_등록한다() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title(게시글제목1)
                .content(게시글내용1)
                .exhibition(전시회제목1)
                .exhibitionAttendance(전시관람인원1)
                .openChatUrl(오픈채팅방Url1)
                .togetherActivity(함께하고싶은활동1)
                .possibleTimes(전시관람희망날짜1)
                .openChatUrl(오픈채팅방Url1)
                .togetherActivity(함께하고싶은활동1)
                .imageName(게시글이미지1)
                .postStatus(모집상태1)
                .build();

        // when & then
        mockMvc.perform(post("/api/posts/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("등록된 게시글을 모두 조회한다.")
    @Test
    void 등록된_게시글을_모두_조회한다() throws Exception {
        // given
        List<PostResponse> responses = Collections.singletonList(PostResponse.builder()
                .id(1L)
                .title("게시글 제목")
                .exhibition("전시회 제목")
                .exhibitionAttendanceAndTogetherActivity(Collections.singletonList("4인 관람"))
                .postStatus(PostStatus.HOLDING)
                .imageName("이미지 이름")
                .createDateTime(LocalDate.now())
                .build());

        when(postService.findPosts()).thenReturn(new PostsResponse(responses));

        // when & then
        mockMvc.perform(get("/api/posts")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(responses))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts[0].id").value(1L))
                .andExpect(jsonPath("$.posts[0].title").value("게시글 제목"))
                .andExpect(jsonPath("$.posts[0].exhibition").value("전시회 제목"))
                .andReturn();
    }

    @DisplayName("게시글에 대한 상세 페이지를 조회한다.")
    @Test
    void 게시글에_대한_상세_페이지를_조회한다() throws Exception {
        // given
        Member 팬시 = 팬시();
        Long postId = 1L;
        PostDetailResponse response = PostDetailResponse.builder()
                .id(postId)
                .writerId(팬시.getId())
                .writerName(팬시.getDisplayName())
                .writerImage(팬시.getMainImage())
                .title(게시글제목1)
                .content(게시글내용1)
                .exhibition(전시회제목1)
                .exhibitionAttendanceAndTogetherActivity(AttendanceAndTogetherActivity(전시관람인원1, 함께하고싶은활동1))
                .possibleTimes(전시관람희망날짜1)
                .openChatUrl(오픈채팅방Url1)
                .postStatus(모집상태1)
                .imageName(게시글이미지1)
                .build();
        when(postService.findPost(anyLong())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/posts/{id}", postId)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.writerName").value("팬시"))
                .andReturn();
    }
}