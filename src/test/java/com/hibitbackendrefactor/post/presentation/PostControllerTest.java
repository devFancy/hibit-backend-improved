package com.hibitbackendrefactor.post.presentation;

import com.hibitbackendrefactor.ControllerTestSupport;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.request.PostUpdateRequest;
import com.hibitbackendrefactor.post.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static com.hibitbackendrefactor.post.dto.response.PostResponse.AttendanceAndTogetherActivity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        given(postService.save(any(), any())).willReturn(프로필_등록_응답());

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/posts/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("posts/save/success",
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
                                        fieldWithPath("postStatus").type(JsonFieldType.STRING).optional().description("모집상태 타입(HOLDING | CANCELLED | COMPLETED)")),
                                responseFields(
                                        fieldWithPath("meta.code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("meta.message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 ID"),
                                        fieldWithPath("data.writerId").type(JsonFieldType.NUMBER).description("로그인한 사용자 ID"),
                                        fieldWithPath("data.writerName").type(JsonFieldType.STRING).description("로그인한 사용자 닉네임"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("data.exhibition").type(JsonFieldType.STRING).description("전시회 제목"),
                                        fieldWithPath("data.exhibitionAttendanceAndTogetherActivity").type(JsonFieldType.ARRAY).description("[ \"3인 관람\", \"맛집 가기\" ]"),
                                        fieldWithPath("data.possibleTime").type(JsonFieldType.STRING).description("관람 희망 날짜"),
                                        fieldWithPath("data.openChatUrl").type(JsonFieldType.STRING).description("오픈 채팅방 URL 주소"),
                                        fieldWithPath("data.togetherActivity").type(JsonFieldType.STRING).optional().description("함께하고싶은 활동 타입(EAT | CAFE | ONLY | LATER)"),
                                        fieldWithPath("data.imageName").type(JsonFieldType.STRING).description("게시글 이미지"),
                                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).optional().description("모집상태 타입(HOLDING | CANCELLED | COMPLETED)"),
                                        fieldWithPath("data.viewCount").type(JsonFieldType.NUMBER).description("게시글 조회수"))
                        )
                )
                .andExpect(status().isCreated());
    }

    @DisplayName("등록된 게시글을 모두 조회한다.")
    @Test
    void 등록된_게시글을_모두_조회한다() throws Exception {
        // given
        Member 팬시 = 팬시();
        팬시 = memberRepository.save(팬시);
        List<Post> 게시글_목록 = List.of(프로젝트_해시테크(팬시), 오스틴리_전시회(팬시));
        PostsResponse response = PostsResponse.of(게시글_목록);

        given(postService.findAll()).willReturn(response);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("posts/find/all/success",
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
        memberRepository.save(팬시);

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
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/{id}", postId)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("posts/find/one/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("게시글 ID")
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("검색할 때 특정 제목 또는 내용에 해당하는 값을 입력하면 해당 값이 포함된 개수가 반환된다.")
    @Test
    void searchPostCount() throws Exception {
        // given
        PostsCountResponse countResponse = new PostsCountResponse(5);

        // when
        when(postService.countPostWithQuery(any())).thenReturn(countResponse);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/count?query=제목|내용")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("posts/count/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
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
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/search?query=제목&size=2&page=0")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("posts/search/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk());
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
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/search?query=제목2|제목1&size=2&page=0")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("posts/search/success/or",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk());
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
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/posts/search?query=제목2&제목1&size=2&page=0")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("posts/search/success/and",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글의 일부 속성을 수정하면 204를 반환한다.")
    @Test
    void 게시글의_일부_속성을_수정한다() throws Exception {
        // given
        Long postId = 1L;
        willDoNothing()
                .given(postService)
                .update(any(), any(), any());

        PostUpdateRequest request = PostUpdateRequest.builder()
                .title(게시글제목2)
                .content(게시글내용2)
                .exhibition(전시회제목2)
                .exhibitionAttendance(전시관람인원2)
                .possibleTime(전시관람희망날짜2)
                .openChatUrl(오픈채팅방Url2)
                .togetherActivity(함께하고싶은활동2)
                .imageName(게시글이미지2)
                .postStatus(모집상태2)
                .build();

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/posts/{postId}", postId)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("posts/update/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isNoContent());
    }

    @DisplayName("본인이 등록한 게시글을 삭제하면 204를 반환한다.")
    @Test
    void 본인이_등록한_게시글을_삭제하면_204를_반환한다() throws Exception {
        // given
        Long postId = 1L;
        willDoNothing()
                .given(postService)
                .update(any(), any(), any());

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/posts/{postId}", postId)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("posts/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isNoContent());
    }
}