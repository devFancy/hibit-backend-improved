package com.hibitbackendrefactor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibitbackendrefactor.auth.application.AuthService;
import com.hibitbackendrefactor.auth.application.OAuthUri;
import com.hibitbackendrefactor.auth.presentation.AuthController;
import com.hibitbackendrefactor.config.ExternalApiConfig;
import com.hibitbackendrefactor.member.application.MemberService;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.member.presentation.MemberController;
import com.hibitbackendrefactor.post.application.PostService;
import com.hibitbackendrefactor.post.presentation.PostController;
import com.hibitbackendrefactor.profile.application.ProfileService;
import com.hibitbackendrefactor.profile.presentation.ProfileController;
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
