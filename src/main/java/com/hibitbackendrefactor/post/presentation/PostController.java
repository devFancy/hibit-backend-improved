package com.hibitbackendrefactor.post.presentation;

import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendrefactor.post.application.PostService;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.PostDetailResponse;
import com.hibitbackendrefactor.post.dto.response.PostResponse;
import com.hibitbackendrefactor.post.dto.response.PostsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Tag(name = "posts", description = "매칭 게시글")
@RestController
public class PostController {
    private static final int PAGE_SIZE = 6;

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/posts/new")
    @Operation(summary = "/api/posts/new", description = "게시글을 등록한다.")
    public ResponseEntity<Post> save(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember
            , @Valid @RequestPart PostCreateRequest request
            , @RequestPart final List<MultipartFile> multipartFiles) throws IOException {
        Long postId = postService.save(loginMember.getId(), request, multipartFiles);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

    @GetMapping("api/posts")
    @Operation(summary = "/api/posts", description = "등록된 게시글 전체를 조회한다.")
    public ResponseEntity<PostsResponse> findAllPosts(@PageableDefault(size = PAGE_SIZE) Pageable pageable) {
        PostsResponse responses = postService.findAllByPosts(pageable);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("api/posts/{id}")
    @Operation(summary = "/api/posts/{id}", description = "게시글에 대한 상세 페이지를 조회한다.")
    public ResponseEntity<PostDetailResponse> findPost(@PathVariable(name = "id") Long postId) {
        PostDetailResponse response = postService.findPost(postId);
        return ResponseEntity.ok(response);
    }

}
