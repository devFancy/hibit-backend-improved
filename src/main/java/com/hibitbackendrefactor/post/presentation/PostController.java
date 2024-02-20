package com.hibitbackendrefactor.post.presentation;

import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendrefactor.post.application.PostService;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/api/posts/new")
    public ResponseEntity<Void> save(@AuthenticationPrincipal final LoginMember loginMember
            , @Valid @RequestBody final PostCreateRequest request) {
        Long postId = postService.save(loginMember.getId(), request);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

    @GetMapping(path = "/api/posts")
    public ResponseEntity<PostsResponse> findAll() {
        PostsResponse responses = postService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/api/posts/{id}")
    public ResponseEntity<PostDetailResponse> findPost(@PathVariable(name = "id") final Long postId,
                                                       @AuthenticationPrincipal final LoginMember loginMember,
                                                       @CookieValue(value = "viewedPost", required = false, defaultValue = "") final String postLog) {
        PostDetailResponse response = postService.findPost(postId, loginMember, postLog);
        String updatedLog = postService.updatePostLog(postId, postLog);
        ResponseCookie responseCookie = ResponseCookie.from("viewedPost", updatedLog).maxAge(86400L).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(response);
    }

    @GetMapping(path = "/api/posts/count")
    public ResponseEntity<PostsCountResponse> searchPostCount(@RequestParam @Nullable String query) {
        PostsCountResponse postsCountResponse = postService.countPostWithQuery(query);
        return ResponseEntity.ok(postsCountResponse);
    }

    @GetMapping(path = "/api/posts/search")
    public ResponseEntity<PostsSliceResponse> searchSlicePosts(@RequestParam @Nullable String query,
                                                               @PageableDefault(sort = "created_date_time", direction = DESC) Pageable pageable) {
        PostsSliceResponse postsSliceResponse = postService.searchSlickWithQuery(query, pageable);
        return ResponseEntity.ok(postsSliceResponse);
    }

}
