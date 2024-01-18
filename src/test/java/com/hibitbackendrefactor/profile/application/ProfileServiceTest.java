package com.hibitbackendrefactor.profile.application;

import com.hibitbackendrefactor.IntegrationTestSupport;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.profile.domain.*;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.팬시_프로필;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @AfterEach
    void tearDown() {
        profileRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("프로필을 등록한다.")
    @Test
    void 프로필을_등록한다()  {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);

        ProfileCreateRequest request = ProfileCreateRequest.builder()
                .nickname("devFancy")
                .age(28)
                .gender(0)
                .personality(PersonalityType.TYPE_1)
                .introduce("안녕하세요 개발자 팬시입니다.")
                .imageName("image.png")
                .job("개발자")
                .addressCity(AddressCity.SEOUL)
                .addressDistrict(AddressDistrict.SEOUL_GANGNAM)
                .jobVisibility(true)
                .addressVisibility(false)
                .myImageVisibility(false)
                .build();

        // when
        Long newProfileId = profileService.save(팬시.getId(), request);
        Profile savedProfile = profileRepository.findByMemberId(팬시.getId()).orElse(null);

        // then
        assertThat(newProfileId).isNotNull();
        assertNotNull(savedProfile);
        assertEquals("devFancy", savedProfile.getNickname());
    }

    @DisplayName("본인의 프로필을 조회한다.")
    @Test
    void 본인의_프로필을_조회한다() {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);
        Profile profile = 팬시_프로필();
        Long memberId = profileRepository.save(profile).getMember().getId();

        // when
        ProfileResponse response = profileService.findMyProfile(memberId);

        // then
        assertAll(
                () -> assertThat(response.getNickname()).isEqualTo(profile.getNickname()),
                () -> assertThat(response.getAge()).isEqualTo(profile.getAge()),
                () -> assertThat(response.getGender()).isEqualTo(profile.getGender()),
                () -> assertThat(response.getPersonality()).isEqualTo(profile.getPersonality()),
                () -> assertThat(response.getIntroduce()).isEqualTo(profile.getIntroduce()),
                () -> assertThat(response.getJob()).isEqualTo(profile.getJob())
        );

    }

    @DisplayName("존재하지 않는 프로필을 조회하면 예외가 발생한다")
    @Test
    void 존재하지_않는_프로필을_조회하면_예외가_발생한다() {
        // given
        Long 잘못된_아이디 = 0L;

        // when & then
        assertThatThrownBy(() -> profileService.findMyProfile(잘못된_아이디))
                .isInstanceOf(NotFoundProfileException.class);
    }
}