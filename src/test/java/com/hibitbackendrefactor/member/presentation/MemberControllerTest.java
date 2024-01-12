package com.hibitbackendrefactor.member.presentation;

import com.hibitbackendrefactor.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.hibitbackendrefactor.common.AuthFixtures.더미_엑세스_토큰;
import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시_응답;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String AUTHORIZATION_HEADER_VALUE = "Bearer aaaaaaaa.bbbbbbbb.cccccccc";

    @DisplayName("본인의 회원 정보를 조회한다.")
    @Test
    void 본인의_회원_정보를_조회한다() throws Exception {
        // given
        given(memberService.findById(팬시_응답.getId())).willReturn(팬시_응답);
        given(authService.extractMemberId(더미_엑세스_토큰)).willReturn(팬시_응답.getId());

        // when & then
        mockMvc.perform(get("/api/members/me")
                        .header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}