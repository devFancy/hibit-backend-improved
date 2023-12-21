package com.hibitbackendrefactor.post.application;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostRepository;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public Post save(PostCreateRequest newPostRequest, Long memberId) {

        Member foundMember = memberRepository.getById(memberId);
        Post post = createPost(newPostRequest, foundMember);


        return null;
    }

    private Post createPost(PostCreateRequest newPostRequest, Member foundMember) {
        return Post.builder()
                .title(newPostRequest.getTitle())
                .exhibition(newPostRequest.getExhibition())
                .exhibitionAttendance(newPostRequest.getExhibitionAttendance())
                .openChatUrl(newPostRequest.getOpenChatUrl())
                .togetherActivity(newPostRequest.getTogetherActivity())
                .content(newPostRequest.getContent())
                .postStatus(newPostRequest.getPostStatus())
                .possibleTime(newPostRequest.getPossibleTime())
                .build();
    }
}
