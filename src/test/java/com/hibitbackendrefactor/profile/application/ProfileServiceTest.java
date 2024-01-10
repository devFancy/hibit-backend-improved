package com.hibitbackendrefactor.profile.application;

import com.hibitbackendrefactor.global.s3.S3UploadService;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.profile.domain.*;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @MockBean
    private S3UploadService s3UploadService;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @AfterEach
    void tearDown() {
        profileImageRepository.deleteAllInBatch();
        profileRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("프로필을 등록한다.")
    @Test
    void 프로필을_등록한다() throws IOException {
        // given
        Member 팬시 = 팬시();
        memberRepository.save(팬시);

        List<MultipartFile> multipartFiles = Arrays.asList(
                createMockMultipartFile("image1.jpg", "image/jpeg"),
                createMockMultipartFile("image2.png", "image/png")
        );

        ProfileCreateRequest request = ProfileCreateRequest.builder()
                .nickname("devFancy")
                .age(28)
                .gender(0)
                .personality(PersonalityType.TYPE_1)
                .introduce("안녕하세요 개발자 팬시입니다.")
                .images(null)
                .job("개발자")
                .addressCity(AddressCity.SEOUL)
                .addressDistrict(AddressDistrict.SEOUL_GANGNAM)
                .jobVisibility(true)
                .addressVisibility(false)
                .myImageVisibility(false)
                .build();

        // when
        Long newProfileId = profileService.saveMyProfile(팬시.getId(), request, multipartFiles);
        Profile savedProfile = profileRepository.findByMemberId(팬시.getId()).orElse(null);
        List<ProfileImage> savedImages = profileImageRepository.findByProfileId(savedProfile.getId());

        // then
        assertThat(newProfileId).isNotNull();
        assertNotNull(savedProfile);
        assertEquals("devFancy", savedProfile.getNickname());
        assertEquals(2, savedImages.size());
    }

    // MockMultipartFile 생성 메서드
    private MockMultipartFile createMockMultipartFile(String fileName, String contentType) throws IOException {
        return new MockMultipartFile(
                "images",
                fileName,
                contentType,
                "test image content".getBytes()
        );
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