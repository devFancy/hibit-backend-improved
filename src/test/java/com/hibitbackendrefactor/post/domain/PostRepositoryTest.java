package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.IntegrationTestSupport;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.프로젝트_해시테크;
import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.팬시_프로필;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
class PostRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostRepository postRepository;

    private Member member;

    private Profile profile;

    private Post post;

    @BeforeEach
    void setUp() {
        member = 팬시();
        memberRepository.save(member);
        profile = 팬시_프로필(member);
        profileRepository.save(profile);
        post = 프로젝트_해시테크(member);
        postRepository.save(post);
    }

    @DisplayName("게시글과 회원 테이블이 정상적으로 매핑이 된다.")
    @Test
    void 게시글과_회원_테이블이_정상적으로_매핑이_된다() {
        // given
        Post foundPost = postRepository.getById(post.getId());

        // when & then
        Assertions.assertThat(foundPost.getMember().getId()).isNotNull();
    }

    @DisplayName("특정 회원 ID에 해당하는 게시글을 찾는다.")
    @Test
    void 특정_회원_ID에_해당하는_게시글을_찾는다() {
        // given
        Long memberId = post.getMember().getId();

        // when
        Optional<Post> actual = postRepository.findByMemberId(memberId);

        // then
        assertThat(actual).isPresent();
        Post foundPost = actual.get();
        assertThat(foundPost.getMember().getId()).isEqualTo(memberId);
    }
}