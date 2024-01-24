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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.팬시_프로필;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class PostServiceTest extends IntegrationTestSupport {

    private static final String EMPTY_COOKIE_VALUE = "";

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
                () -> assertEquals(게시글제목1, savedPost.getTitle()),
                () -> assertEquals(게시글내용1, savedPost.getContent()),
                () -> assertEquals(전시회제목1, savedPost.getExhibition()),
                () -> assertEquals(전시관람인원1, savedPost.getExhibitionAttendance()),
                () -> assertEquals(전시관람희망날짜1, savedPost.getPossibleTime()),
                () -> assertEquals(오픈채팅방Url1, savedPost.getOpenChatUrl()),
                () -> assertEquals(함께하고싶은활동1, savedPost.getTogetherActivity()),
                () -> assertEquals(전시관람인원1, savedPost.getExhibitionAttendance()),
                () -> assertEquals(게시글이미지1, savedPost.getImageName())
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
        Profile profile = profileRepository.save(팬시_프로필);
        memberRepository.save(팬시);

        Post post = 프로젝트_해시테크(profile.getMember());
        Long savedPostId = postRepository.save(post).getId();

        // when
        PostDetailResponse response = postService.findPost(savedPostId, LOGIN_MEMBER,EMPTY_COOKIE_VALUE);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(post.getId()),
                () -> assertThat(response.getWriterId()).isEqualTo(post.getMember().getId()),
                () -> assertThat(response.getWriterName()).isEqualTo(post.getMember().getDisplayName()),
                () -> assertThat(response.getTitle()).isEqualTo(post.getTitle()),
                () -> assertThat(response.getContent()).isEqualTo(post.getContent()),
                () -> assertThat(response.getExhibition()).isEqualTo(post.getExhibition())
        );
    }

    @DisplayName("특정 게시글을 조회하면 조회수를 1 증가시킨다.")
    @Test
    void 특정_게시글을_조회하면_조회수를_1_증가시킨다() {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);
        Member member = memberRepository.getById(팬시.getId());

        Profile 팬시_프로필 = 팬시_프로필(member);
        Profile profile = profileRepository.save(팬시_프로필);
        memberRepository.save(팬시);

        Post post = 프로젝트_해시테크(profile.getMember());
        postRepository.save(post);

        // when
        int viewCount = post.getViewCount();
        postService.findPost(post.getId(), LOGIN_MEMBER,EMPTY_COOKIE_VALUE);

        int updatedViewCount = postRepository.findById(post.getId()).get().getViewCount();

        // then
        assertThat(viewCount + 1).isEqualTo(updatedViewCount);
    }

    @DisplayName("오늘 처음 방문한 게시글을 조회하면 조회수가 1 증가되고, 이미 방문한 게시글을 조회하면 조회수가 증가하지 않는다.")
    @ParameterizedTest
    @MethodSource("argsOfFindPostViewCount")
    void 오늘_처음_방문한_게시글을_조회하면_조회수가_1_증가되고_이미_방문한_게시글을_조회하면_조회수가_증가하지_않는다(String logs, int expectedIncreasedViewCount) {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);
        Member member = memberRepository.getById(팬시.getId());

        Profile 팬시_프로필 = 팬시_프로필(member);
        Profile profile = profileRepository.save(팬시_프로필);
        memberRepository.save(팬시);

        Post 게시글 = 프로젝트_해시테크(profile.getMember());
        postRepository.save(게시글);

        int viewCount = 게시글.getViewCount();
        postService.findPost(게시글.getId(), LOGIN_MEMBER, logs);
        log.info("viewCount={}", viewCount);

        // when
        int updatedViewCount = postRepository.findById(게시글.getId())
                .get()
                .getViewCount();

        log.info("expectedIncreasedViewCount={}", expectedIncreasedViewCount);
        log.info("updatedViewCount={}", updatedViewCount);

        // then
        assertThat(viewCount + expectedIncreasedViewCount).isEqualTo(updatedViewCount);
    }

    private static Stream<Arguments> argsOfFindPostViewCount() {
        int today = LocalDateTime.now().getDayOfMonth();
        return Stream.of(
                Arguments.of(EMPTY_COOKIE_VALUE, 1),
                Arguments.of(today + ":2/3", 1)
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