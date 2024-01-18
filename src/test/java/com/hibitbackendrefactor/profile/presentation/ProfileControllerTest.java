package com.hibitbackendrefactor.profile.presentation;

import com.hibitbackendrefactor.ControllerTestSupport;
import com.hibitbackendrefactor.profile.domain.AddressCity;
import com.hibitbackendrefactor.profile.domain.AddressDistrict;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
                .nickname("팬시")
                .age(28)
                .gender(1)
                .personality(PersonalityType.TYPE_1)
                .introduce("안녕하세요 저는 소프트웨어 개발자, 팬시입니다.")
                .imageName("fancy.png")
                .addressCity(AddressCity.SEOUL)
                .addressDistrict(AddressDistrict.SEOUL_DOBONG)
                .jobVisibility(true)
                .addressVisibility(false)
                .myImageVisibility(false)
                .build();
        // when & then
        mockMvc.perform(post("/api/profiles/new")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("본인 프로필을 수정한다.")
    @Test
    void 본인_프로필을_수정한다() throws Exception {
        // given
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .nickname("팬시2")
                .age(28)
                .gender(0)
                .personality(PersonalityType.TYPE_1)
                .introduce("안녕하세요 저는 소프트웨어 개발자, 팬시2입니다.")
                .imageName("fancy2.png")
                .addressCity(AddressCity.SEOUL)
                .addressDistrict(AddressDistrict.SEOUL_DOBONG)
                .jobVisibility(false)
                .addressVisibility(true)
                .myImageVisibility(true)
                .build();

        // when & then
        mockMvc.perform(put("/api/profiles/me")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

     }
}