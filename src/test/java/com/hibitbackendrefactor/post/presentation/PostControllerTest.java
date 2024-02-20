package com.hibitbackendrefactor.post.presentation;

import com.hibitbackendrefactor.ControllerTestSupport;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static com.hibitbackendrefactor.post.dto.response.PostResponse.AttendanceAndTogetherActivity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class PostControllerTest extends ControllerTestSupport {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";


    private static final PostResponse POST_RESPONSE_1 = PostResponse.builder()
            .id(1L)
            .title("게시글 제목1")
            .exhibition("전시회 제목1")
            .exhibitionAttendanceAndTogetherActivity(Arrays.asList("4인 관람", "맛집가기"))
            .postStatus(PostStatus.HOLDING)
            .imageName("게시글 이미지1")
            .createDateTime(LocalDateTime.now())
            .build();

    private static final PostResponse POST_RESPONSE_2 = PostResponse.builder()
            .id(2L)
            .title("게시글 제목2")
            .exhibition("전시회 제목2")
            .exhibitionAttendanceAndTogetherActivity(Arrays.asList("3인 관람", "만나서 정해요!"))
            .postStatus(PostStatus.HOLDING)
            .imageName("게시글 이미지2")
            .createDateTime(LocalDateTime.now())
            .build();

    @DisplayName("신규 게시글을 등록한다.")
    @Test
    void 신규_게시글을_등록한다() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title(게시글제목1)
                .content(게시글내용1)
                .exhibition(전시회제목1)
                .exhibitionAttendance(전시관람인원1)
                .openChatUrl(오픈채팅방Url1)
                .togetherActivity(함께하고싶은활동1)
                .possibleTime(전시관람희망날짜1)
                .openChatUrl(오픈채팅방Url1)
                .togetherActivity(함께하고싶은활동1)
                .imageName(게시글이미지1)
                .postStatus(모집상태1)
                .build();

        // when & then
        mockMvc.perform(post("/api/posts/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("post/save",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("exhibition").type(JsonFieldType.STRING).description("전시회 제목"),
                                        fieldWithPath("exhibitionAttendance").type(JsonFieldType.NUMBER).description("참가할 모집 인원"),
                                        fieldWithPath("possibleTime").type(JsonFieldType.STRING).description("관람 희망 날짜"),
                                        fieldWithPath("openChatUrl").type(JsonFieldType.STRING).description("오픈 채팅방 URL 주소"),
                                        fieldWithPath("togetherActivity").type(JsonFieldType.STRING).optional().description("함께하고싶은 활동 타입(EAT | CAFE | ONLY | LATER)"),
                                        fieldWithPath("imageName").type(JsonFieldType.STRING).description("게시글 이미지"),
                                        fieldWithPath("postStatus").type(JsonFieldType.STRING).optional().description("모집상태 타입(HOLDING | CANCELLED | COMPLETED)"))
                        )
                )
                .andExpect(status().isCreated());
    }

    @DisplayName("등록된 게시글을 모두 조회한다.")
    @Test
    void 등록된_게시글을_모두_조회한다() throws Exception {
        // given
        List<PostResponse> responses = Collections.singletonList(PostResponse.builder()
                .id(팬시().getId())
                .title(게시글제목1)
                .exhibition(전시회제목1)
                .exhibitionAttendanceAndTogetherActivity(Collections.singletonList("4인 관람"))
                .postStatus(모집상태1)
                .imageName(게시글이미지1)
                .createDateTime(LocalDateTime.now())
                .build());

        // when
        when(postService.findAll()).thenReturn(new PostsResponse(responses));

        // then
        mockMvc.perform(get("/api/posts")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(responses))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("post/findAll",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk());
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
                .title(게시글제목1)
                .content(게시글내용1)
                .exhibition(전시회제목1)
                .exhibitionAttendanceAndTogetherActivity(AttendanceAndTogetherActivity(전시관람인원1, 함께하고싶은활동1))
                .possibleTime(전시관람희망날짜1)
                .openChatUrl(오픈채팅방Url1)
                .postStatus(모집상태1)
                .imageName(게시글이미지1)
                .build();

        // when
        when(postService.findPost(any(), any(), any())).thenReturn(response);

        // then
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

    @DisplayName("검색할 때 특정 제목 또는 내용에 해당하는 값을 입력하면 해당 값이 포함된 개수가 반환된다.")
    @Test
    void searchPostCount() throws Exception {
        // given
        PostsCountResponse countResponse = new PostsCountResponse(5);

        // when
        when(postService.countPostWithQuery(any())).thenReturn(countResponse);

        // then
        mockMvc.perform(get("/api/posts/count?query=제목|내용")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(countResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("특정 게시글 검색시 200을 반환한다.")
    @Test
    void searchSlicePosts() throws Exception {
        // given
        PostsSliceResponse pagePostsResponse = new PostsSliceResponse(
                List.of(POST_RESPONSE_2, POST_RESPONSE_1), true);

        // when
        when(postService.searchSlickWithQuery(any(), any())).thenReturn(pagePostsResponse);

        // then
        mockMvc.perform(get("/api/posts/search?query=제목&size=2&page=0")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(pagePostsResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts[0].id").value(2L))
                .andExpect(jsonPath("$.posts[0].title").value("게시글 제목2"))
                .andExpect(jsonPath("$.posts[0].exhibition").value("전시회 제목2"))
                .andReturn();

    }

    @DisplayName("or 게시글 검색 시 200을 반환한다.")
    @Test
    void searchSlicePosts_or() throws Exception {
        // given
        PostsSliceResponse pagePostsResponse = new PostsSliceResponse(
                List.of(POST_RESPONSE_2, POST_RESPONSE_1), true);

        // when
        when(postService.searchSlickWithQuery(any(), any())).thenReturn(pagePostsResponse);

        // then
        mockMvc.perform(get("/api/posts/search?query=제목2|제목1&size=2&page=0")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(pagePostsResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts[0].id").value(2L))
                .andExpect(jsonPath("$.posts[0].title").value("게시글 제목2"))
                .andExpect(jsonPath("$.posts[0].exhibition").value("전시회 제목2"))
                .andReturn();

    }

    @DisplayName("and 게시글 검색 시 200을 반환한다.")
    @Test
    void searchSlicePosts_and() throws Exception {
        // given
        PostsSliceResponse pagePostsResponse = new PostsSliceResponse(
                List.of(POST_RESPONSE_2, POST_RESPONSE_1), true);

        // when
        when(postService.searchSlickWithQuery(any(), any())).thenReturn(pagePostsResponse);

        // then
        mockMvc.perform(get("/api/posts/search?query=제목2&제목1&size=2&page=0")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(pagePostsResponse))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.posts[0].id").value(2L))
                .andExpect(jsonPath("$.posts[0].title").value("게시글 제목2"))
                .andExpect(jsonPath("$.posts[0].exhibition").value("전시회 제목2"))
                .andReturn();
    }
}