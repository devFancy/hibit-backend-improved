package com.hibitbackendrefactor.post.application;

import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.exception.AuthorizationException;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostRepository;
import com.hibitbackendrefactor.post.domain.SearchQuery;
import com.hibitbackendrefactor.post.domain.ViewCountManager;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.request.PostUpdateServiceRequest;
import com.hibitbackendrefactor.post.dto.response.PostDetailResponse;
import com.hibitbackendrefactor.post.dto.response.PostsCountResponse;
import com.hibitbackendrefactor.post.dto.response.PostsResponse;
import com.hibitbackendrefactor.post.dto.response.PostsSliceResponse;
import com.hibitbackendrefactor.post.exception.NotFoundPostException;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final ViewCountManager viewCountManager;

    public PostService(final PostRepository postRepository, final MemberRepository memberRepository,
                       final ProfileRepository profileRepository, final ViewCountManager viewCountManager) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
        this.viewCountManager = viewCountManager;
    }

    @Transactional
    public Long save(final Long memberId, final PostCreateRequest request) {
        validateMember(memberId);
        Member foundMember = memberRepository.getById(memberId);

        Post savedPost = request.toEntity(foundMember, request);
        postRepository.save(savedPost);

        return savedPost.getId();
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

    public PostsResponse findAll() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDateTimeDesc();
        return PostsResponse.of(posts);
    }

    @Transactional
    public PostDetailResponse findPost(final Long postId, final LoginMember loginMember, final String cookieValue) {
        if (viewCountManager.isFirstAccess(cookieValue, postId)) {
            postRepository.updateViewCount(postId);
        }
        Post foundPost = findPostObject(postId);
        return PostDetailResponse.of(foundPost, loginMember);
    }

    public String updatePostLog(final Long postId, final String cookieValue) {
        return viewCountManager.getUpdatedLog(cookieValue, postId);
    }

    private Post findPostObject(final Long postId) {
        List<Post> posts = postRepository.findPostById(postId);
        if (posts.isEmpty()) {
            throw new NotFoundPostException();
        }
        return posts.get(0);
    }

    public PostsCountResponse countPostWithQuery(final String query) {
        Pageable pageable = PageRequest.of(0, 3, DESC, "created_date_time");
        SearchQuery searchQuery = new SearchQuery(query);

        Page<Post> posts = postRepository.findPostPagesByQuery(pageable, searchQuery.getValue());
        return PostsCountResponse.of((int) posts.getTotalElements());
    }

    public PostsSliceResponse searchSlickWithQuery(final String query, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), DESC, "created_date_time");
        SearchQuery searchQuery = new SearchQuery(query);

        Slice<Post> posts = postRepository.findPostSlicePageByQuery(pageable, searchQuery.getValue());
        return PostsSliceResponse.ofPostSlice(posts);
    }

    @Transactional
    public void update(final LoginMember loginMember, final Long postId, final PostUpdateServiceRequest request) {
        Member member = memberRepository.getById(loginMember.getId());
        Post post = findPostObject(postId);
        validateProductMembership(loginMember, post);

        post.change(member, request.getTitle(), request.getContent(), request.getExhibition(), request.getExhibitionAttendance(), request.getPossibleTime(), request.getOpenChatUrl(),
                request.getTogetherActivity(), request.getImageName(), request.getPostStatus());
    }

    private void validateProductMembership(final LoginMember loginMember, final Post post) {
        if(!post.isMember(loginMember.getId())) {
            throw new AuthorizationException();
        }
    }
}
