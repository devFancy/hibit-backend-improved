package com.hibitbackendrefactor.post.application;

import com.hibitbackendrefactor.IntegrationTestSupport;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostRepository;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.post.dto.response.PostDetailResponse;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.팬시_프로필;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostServiceTest extends IntegrationTestSupport {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;
    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        profileRepository.deleteAll();
        memberRepository.deleteAll(); // deleteAllInBatch()인 경우 오류는 나중에 해결할 예정(23.01.10)
    }

    @DisplayName("게시글을 등록한 페이지를 반환한다.")
    @Test
    void 게시글을_등록한_페이지를_반환한다() {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);
        Member member = memberRepository.getById(팬시.getId());

        Profile 팬시_프로필 = 팬시_프로필(member);
        profileRepository.save(팬시_프로필);

        // when
        PostCreateRequest request = getPostCreateRequest();
        Long newPostId = postService.save(팬시_프로필.getMember().getId(), request);
        Post savedPost = postRepository.findByMemberId(팬시.getId()).orElse(null);

        // then
        assertThat(newPostId).isNotNull();
        assertAll(
                () -> assertEquals("프로젝트_해시테크", savedPost.getTitle()),
                () -> assertEquals("프로젝트 해시 태크(http://projecthashtag.net/) 보러가실 분 있으면 아래 댓글 남겨주세요~", savedPost.getContent()),
                () -> assertEquals("PROJECT HASHTAG 2023 SELECTED ARTISTS", savedPost.getExhibition()),
                () -> assertEquals(3, savedPost.getExhibitionAttendance()),
                () -> assertEquals("postImage1.png", savedPost.getImageName())
        );
    }

    @DisplayName("게시글에 대한 상세 페이지를 반환한다.")
    @Test
    void 게시글에_대한_상세_페이지를_반환한다() {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);
        Member member = memberRepository.getById(팬시.getId());

        Profile 팬시_프로필 = 팬시_프로필(member);
        Profile profile =  profileRepository.save(팬시_프로필);
        memberRepository.save(팬시);

        Post post = 프로젝트_해시테크(profile.getMember());
        Long savedPostId = postRepository.save(post).getId();

        // when
        PostDetailResponse response = postService.findPost(savedPostId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(post.getId()),
                () -> assertThat(response.getWriterId()).isEqualTo(post.getMember().getId()),
                () -> assertThat(response.getWriterName()).isEqualTo(post.getMember().getDisplayName()),
//                () -> assertThat(response.getWriterImage()).isEqualTo(post.getMember().getMainImage()),
                () -> assertThat(response.getTitle()).isEqualTo(post.getTitle()),
                () -> assertThat(response.getContent()).isEqualTo(post.getContent()),
                () -> assertThat(response.getExhibition()).isEqualTo(post.getExhibition())
        );
    }

    private static PostCreateRequest getPostCreateRequest() {
        PostCreateRequest request = PostCreateRequest.builder()
                .title(게시글제목1)
                .content(게시글내용1)
                .exhibition(전시회제목1)
                .exhibitionAttendance(전시관람인원1)
                .possibleTime(전시관람희망날짜1)
                .openChatUrl(오픈채팅방Url1)
                .togetherActivity(함께하고싶은활동1)
                .imageName(게시글이미지1)
                .postStatus(모집상태1)
                .build();
        return request;
    }
}