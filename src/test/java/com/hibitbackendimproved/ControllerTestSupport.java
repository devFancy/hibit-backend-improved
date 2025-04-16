package com.hibitbackendimproved;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibitbackendimproved.auth.application.AuthService;
import com.hibitbackendimproved.auth.application.OAuthUri;
import com.hibitbackendimproved.auth.presentation.AuthController;
import com.hibitbackendimproved.config.ExternalApiConfig;
import com.hibitbackendimproved.member.application.MemberService;
import com.hibitbackendimproved.member.domain.MemberRepository;
import com.hibitbackendimproved.member.presentation.MemberController;
import com.hibitbackendimproved.post.application.PostService;
import com.hibitbackendimproved.post.presentation.PostController;
import com.hibitbackendimproved.profile.application.ProfileService;
import com.hibitbackendimproved.profile.presentation.ProfileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(controllers = {
        MemberController.class,
        AuthController.class,
        ProfileController.class,
        PostController.class
})
@Import(ExternalApiConfig.class)
@ActiveProfiles("test")
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected OAuthUri oAuthUri;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberRepository memberRepository;

    @MockBean
    protected ProfileService profileService;

    @MockBean
    protected PostService postService;
}
