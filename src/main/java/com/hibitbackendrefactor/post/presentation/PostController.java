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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Tag(name = "posts", description = "매칭 게시글")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/posts/new")
    @Operation(summary = "/api/posts/new", description = "매칭 게시글 작성")
    public ResponseEntity<Post> save(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember
            , @Valid @RequestPart PostCreateRequest request
            , @RequestPart final List<MultipartFile> multipartFiles) throws IOException {
        Long postId = postService.save(loginMember.getId(), request, multipartFiles);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

}
