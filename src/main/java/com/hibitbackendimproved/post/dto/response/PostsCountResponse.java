package com.hibitbackendimproved.post.dto.response;

import lombok.Getter;

@Getter
public class PostsCountResponse {
    private int totalPostCount;

    protected PostsCountResponse() {
    }

    public PostsCountResponse(final int totalPostCount) {
        this.totalPostCount = totalPostCount;
    }


    public static PostsCountResponse of(int totalElements) {
        return new PostsCountResponse(totalElements);
    }
}
