package com.hibitbackendrefactor.profile.presentation;

import com.hibitbackendrefactor.ControllerTestSupport;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import com.hibitbackendrefactor.profile.exception.InvalidProfileAlreadyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileControllerTest extends ControllerTestSupport {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @DisplayName("본인 프로필을 등록한다.")
    @Test
    void 본인_프로필을_등록한다() throws Exception {
        // given
        ProfileCreateRequest request = ProfileCreateRequest.builder()
                .nickname(팬시_닉네임)
                .age(팬시_나이)
                .gender(팬시_나이)
                .personality(팬시_성격)
                .introduce(팬시_자기소개)
                .imageName(팬시_이미지)
                .job(팬시_직업)
                .addressCity(팬시_사는도시)
                .addressDistrict(팬시_사는지역)
                .jobVisibility(직업_공개여부)
                .addressVisibility(주소_공개여부)
                .myImageVisibility(이미지_공개여부)
                .build();
        // when & then
        mockMvc.perform(post("/api/profiles/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("profiles/save/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")),
                        requestFields(
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("age").description("나이"),
                                fieldWithPath("gender").description("성별"),
                                fieldWithPath("personality").description("성격"),
                                fieldWithPath("introduce").description("자기소개"),
                                fieldWithPath("imageName").description("프로필 대표 이미지"),
                                fieldWithPath("job").description("직업"),
                                fieldWithPath("addressCity").description("시"),
                                fieldWithPath("addressDistrict").description("구"),
                                fieldWithPath("jobVisibility").description("직업 공개 여부"),
                                fieldWithPath("addressVisibility").description("주소 공개 여부"),
                                fieldWithPath("myImageVisibility").description("서브 이미지 공개 여부")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @DisplayName("본인의 프로필을 등록할 때 이미 존재하는 닉네임이면 400을 반환한다")
    @Test
    void 본인의_프로필을_등록할_때_이미_존재하는_닉네임이면_400을_반환한다() throws Exception {
        // given
        given(profileService.save(any(), any())).willThrow(new InvalidProfileAlreadyException());

        // when
        mockMvc.perform(post("/api/profiles/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(팬시_프로필()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("profiles/save/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("본인이 선택할 수 있는 성격 목록을 반환한다.")
    @Test
    void 본인이_선택할_수_있는_성격_목록을_반환한다() throws Exception {
        // given
        List<PersonalityType> requests = Arrays.asList(PersonalityType.values());

        // when & then
        mockMvc.perform(get("/api/profiles/personalities")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("profiles/find/personalities/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("본인의 프로필을 조회한다.")
    @Test
    void 본인의_프로필을_조회한다() throws Exception {
        // given
        ProfileResponse response = ProfileResponse.builder()
                .nickname(팬시_닉네임)
                .age(팬시_나이)
                .gender(팬시_성별)
                .personality(팬시_성격)
                .introduce(팬시_자기소개)
                .job(팬시_직업)
                .addressCity(팬시_사는도시)
                .addressDistrict(팬시_사는지역)
                .imageName(팬시_이미지)
                .jobVisibility(직업_공개여부)
                .addressVisibility(주소_공개여부)
                .myImageVisibility(이미지_공개여부)
                .build();

        given(profileService.findMyProfile(any())).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/profiles/me")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("profiles/find/me/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("타인의 프로필을 조회한다")
    @Test
    void 타인의_프로필을_조회한다() throws Exception {
        // given
        Long 타인프로필_id = 2L;
        given(profileService.findOtherProfile(any())).willReturn(타인_프로필_조회_응답());

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/profiles/other/{id}", 타인프로필_id)
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("profiles/find/other/one/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("타인 ID")
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("본인 프로필을 수정한다.")
    @Test
    void 본인_프로필을_수정한다() throws Exception {
        // given
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .nickname(팬시_닉네임2)
                .age(팬시_나이2)
                .gender(팬시_성별2)
                .personality(팬시_성격2)
                .introduce(팬시_자기소개2)
                .imageName(팬시_이미지2)
                .job(팬시_직업2)
                .addressCity(팬시_사는도시2)
                .addressDistrict(팬시_사는지역2)
                .jobVisibility(직업_공개여부)
                .addressVisibility(주소_공개여부)
                .myImageVisibility(이미지_공개여부)
                .build();

        // when & then
        mockMvc.perform(put("/api/profiles/me")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andDo(document("profiles/update/me/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isNoContent());
    }
}