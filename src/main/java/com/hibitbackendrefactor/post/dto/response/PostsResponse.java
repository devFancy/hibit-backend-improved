package com.hibitbackendrefactor.post.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PostsResponse {
    private final List<PostResponse> posts;

    public PostsResponse(List<PostResponse> posts) {
        this.posts = posts;
    }
}
