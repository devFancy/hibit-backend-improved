package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.Post;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsSliceResponse {
    private final List<PostResponse> posts;
    private final boolean lastPage;

    public PostsSliceResponse(final List<PostResponse> posts, final boolean lastPage) {
        this.posts = posts;
        this.lastPage = lastPage;
    }

    public static PostsSliceResponse ofPostSlice(final Page<Post> postSlice) {
        List<PostResponse> postResponses = postSlice.getContent()
                .stream()
                .map(post ->  PostResponse.from(post))
                .collect(Collectors.toList());
        return new PostsSliceResponse(postResponses, postSlice.isLast());
    }
}
