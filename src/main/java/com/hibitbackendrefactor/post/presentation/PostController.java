package com.hibitbackendrefactor.post.presentation;

import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendrefactor.post.application.PostService;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "posts", description = "매칭 게시글")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/posts/new")
    @Operation(summary = "/api/posts/new", description = "매칭 게시글 작성")
    @Parameters({
            @Parameter(name = "title", description = "제목", example = "디뮤지엄 전시 보러가요"),
            @Parameter(name = "exhibition", description = "가고싶은 전시회", example = "오스틴리 전시회"),
            @Parameter(name = "exhibitionAttendance", description = "전시 관람 인원", example = "4"),
            @Parameter(name = "possibleTime", description = "관람 희망 날짜", example = "[\"2023-12-25\"]"),
            @Parameter(name = "openChatUrl", description = "오픈 채팅방 URL", example = "http://kakao"),
            @Parameter(name = "togetherActivity", description = "함께 하고싶은 활동", example = "EAT"),
            @Parameter(name = "content", description = "상세 내용", example = "오스린리 전시회 보러가실 분 있나요?"),
            @Parameter(name = "postImages", description = "게시글 이미지 리스트 URL", example = "[\"image1\", \"image2\", \"image3\"]")
    })
    public ResponseEntity<Post> save(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember, @RequestBody PostCreateRequest request) {
        Post post = postService.save(request, loginMember.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

}
