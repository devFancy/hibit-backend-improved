package com.hibitbackendrefactor.post.application;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostRepository;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.PostDetailResponse;
import com.hibitbackendrefactor.post.dto.response.PostsResponse;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public PostService(final PostRepository postRepository, final MemberRepository memberRepository, final ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Long save(final Long memberId, final PostCreateRequest request) {
        validateMember(memberId);
        Member foundMember = memberRepository.getById(memberId);

        Post post = createPost(request, foundMember);
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    private Post createPost(final PostCreateRequest request, final Member foundMember) {
        return Post.builder()
                .member(foundMember)
                .title(request.getTitle())
                .content(request.getContent())
                .exhibition(request.getExhibition())
                .exhibitionAttendance(request.getExhibitionAttendance())
                .possibleTimes(request.getPossibleTimes())
                .openChatUrl(request.getOpenChatUrl())
                .togetherActivity(request.getTogetherActivity())
                .imageName(request.getImageName())
                .postStatus(PostStatus.HOLDING)
                .build();
    }

    private void validateMember(final Long memberId) {
        if (!existProfile(memberId)) {
            throw new NotFoundProfileException("프로필을 등록해야 게시글을 저장할 수 있습니다.");
        }
    }

    private boolean existProfile(final Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId).orElse(null);
        return profile != null;
    }

    public PostsResponse findAllByPosts(final Pageable pageable) {
        List<Post> posts = postRepository.findAllByOrderByCreatedDateTimeDesc(pageable);
        List<PostResponse> responses = getPosts(posts);
        return new PostsResponse(responses);
    }

    private List<PostResponse> getPosts(final List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    String imageUrl = postImageRepository.findOneImageUrlByPostId(post.getId());
                    return PostResponse.of(post, imageUrl);
                })
                .collect(Collectors.toList());
    }

    public PostDetailResponse findPost(final Long postId) {
        Post post = postRepository.getById(postId);
        List<String> imageUrls = getImageUrls(post);
        return PostDetailResponse.of(post, imageUrls);
    }

    private List<String> getImageUrls(final Post post) {
        return postImageRepository.findAllImageUrlsByPostId(post.getId());
    }
}
