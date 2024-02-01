package com.hibitbackendrefactor.post.presentation;

import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendrefactor.post.application.PostService;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.PostDetailResponse;
import com.hibitbackendrefactor.post.dto.response.PostsCountResponse;
import com.hibitbackendrefactor.post.dto.response.PostsResponse;
import com.hibitbackendrefactor.post.dto.response.PostsSliceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "posts", description = "매칭 게시글")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/api/posts/new")
    @Operation(summary = "/api/posts/new", description = "게시글을 등록한다.")
    public ResponseEntity<Post> save(@Parameter(hidden = true)
                                     @AuthenticationPrincipal final LoginMember loginMember
            , @Valid @RequestBody final PostCreateRequest request) {
        Long postId = postService.save(loginMember.getId(), request);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

    @GetMapping(path = "/api/posts")
    @Operation(summary = "/api/posts", description = "등록된 게시글을 모두 조회한다.")
    public ResponseEntity<PostsResponse> findPosts() {
        PostsResponse responses = postService.findPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/api/posts/{id}")
    @Operation(summary = "/api/posts/{id}", description = "게시글에 대한 상세 페이지를 조회한다.")
    public ResponseEntity<PostDetailResponse> findPost(@PathVariable(name = "id") final Long postId,
                                                       @AuthenticationPrincipal final LoginMember loginMember,
                                                       @CookieValue(value = "viewedPost", required = false, defaultValue = "") final String postLog) {
        PostDetailResponse response = postService.findPost(postId, loginMember, postLog);
        String updatedLog = postService.updatePostLog(postId, postLog);
        ResponseCookie responseCookie = ResponseCookie.from("viewedPost", updatedLog).maxAge(86400L).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(response);
    }

    @GetMapping(path = "/api/posts/count")
    @Operation(summary = "/api/posts/count", description = "특정 제목 또는 내용에 해당하는 값을 입력하면 해당 값이 포함된 게시글의 총 개수를 반환한다.")
    public ResponseEntity<PostsCountResponse> searchPostCount(@RequestParam @Nullable String query) {
        PostsCountResponse postsCountResponse = postService.countPostWithQuery(query);
        return ResponseEntity.ok(postsCountResponse);
    }

    @GetMapping(path = "/api/posts/search")
    @Operation(summary = "/api/posts/search", description = "특정 값을 입력하여 검색하면 해당 값(제목/내용)이 포함된 게시글을 조회한다.")
    public ResponseEntity<PostsSliceResponse> searchSlicePosts(@RequestParam @Nullable String query,
                                                          @PageableDefault(sort = "created_date_time", direction = DESC) Pageable pageable) {
        PostsSliceResponse postsSliceResponse = postService.searchSlickWithQuery(query, pageable);
        return ResponseEntity.ok(postsSliceResponse);
    }

}
