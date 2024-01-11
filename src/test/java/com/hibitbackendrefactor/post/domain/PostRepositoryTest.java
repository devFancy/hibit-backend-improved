package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.브루스;
import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.PostFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@ActiveProfiles("test")
@DataJpaTest
class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;

    @DisplayName("등록된 모든 게시글을 조회한다.")
    @Test
    void 등록된_모든_게시글을_조회한다() {
        // given
        Post post1 = createPost(팬시(), 게시글제목1, 게시글내용1, 전시회제목1, 전시관람인원1, 전시관람희망날짜1, 오픈채팅방Url1, 함께하고싶은활동1, 게시글이미지1, 모집상태1);
        Post post2 = createPost(브루스(), 게시글제목2, 게시글내용2, 전시회제목2, 전시관람인원2, 전시관람희망날짜2, 오픈채팅방Url2, 함께하고싶은활동2, 게시글이미지2, 모집상태2);

        postRepository.saveAll(List.of(post1, post2));

        // when
        List<Post> posts = postRepository.findAllByOrderByCreatedDateTimeDesc();

        // then
        Assertions.assertThat(posts).hasSize(2)
                .extracting("title", "content", "openChatUrl")
                .containsExactlyInAnyOrder(
                        tuple("프로젝트_해시테크", "프로젝트 해시 태크(http://projecthashtag.net/) 보러가실 분 있으면 아래 댓글 남겨주세요~", "http://projecthashtag.net/"),
                        tuple("오스틴리 전시회", "오스틴리 전시회 보고, 같이 담소하게 얘기 나누실 분 있으시면 아래 댓글 남겨주세요~", "http://ostin.net/")
                );
     }


     private Post createPost(Member member, String title, String content,
                             String exhibition, int exhibitionAttendance, List<PostPossibleTime> postPossibleTimes,
                             String openChatUrl, TogetherActivity togetherActivity, String imageName, PostStatus postStatus) {

        Post post1 = Post.builder()
                .member(member)
                .title(title)
                .content(content)
                .exhibition(exhibition)
                .exhibitionAttendance(exhibitionAttendance)
                .possibleTimes(postPossibleTimes)
                .openChatUrl(openChatUrl)
                .togetherActivity(togetherActivity)
                .imageName(imageName)
                .postStatus(postStatus)
                .build();
        return post1;
     }
}