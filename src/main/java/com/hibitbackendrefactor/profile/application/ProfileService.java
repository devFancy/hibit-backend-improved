package com.hibitbackendrefactor.profile.application;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileOtherResponse;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import com.hibitbackendrefactor.profile.exception.InvalidProfileAlreadyException;
import com.hibitbackendrefactor.profile.exception.NicknameAlreadyTakenException;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(final MemberRepository memberRepository, final ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Long save(final Long memberId, final ProfileCreateRequest request) {
        validateExistByNickname(request.getNickname());
        if (profileRepository.existsByMemberId(memberId)) {
            throw new InvalidProfileAlreadyException("프로필이 이미 존재합니다.");
        }
        Member foundMember = memberRepository.getById(memberId);
        Profile profile = createProfile(request, foundMember);
        Profile savedProfile = profileRepository.save(profile);

        updateMemberInfo(foundMember, savedProfile);
        return savedProfile.getId();
    }

    private Profile createProfile(final ProfileCreateRequest request, final Member foundMember) {
        return Profile.builder()
                .member(foundMember)
                .nickname(request.getNickname())
                .age(request.getAge())
                .gender(request.getGender())
                .personality(request.getPersonality())
                .introduce(request.getIntroduce())
                .imageName(request.getImageName())
                .job(request.getJob())
                .addressCity(request.getAddressCity())
                .addressDistrict(request.getAddressDistrict())
                .jobVisible(request.isJobVisibility())
                .addressVisible(request.isAddressVisibility())
                .myImageVisibility(request.isMyImageVisibility())
                .build();
    }

    private void validateExistByNickname(final String nickname) {
        if (profileRepository.existsByNickname((nickname))) {
            throw new NicknameAlreadyTakenException("이미 사용중인 닉네임입니다.");
        }
    }

    private void updateMemberInfo(final Member member, final Profile profile) {
        member.updateDisplayName(profile.getNickname());
        member.updateIsprofile();
        memberRepository.save(member);
    }

    @Transactional
    public void update(final Long memberId, final ProfileUpdateRequest request) {
        Profile profile = profileRepository.findByMemberId(memberId)
                        .orElseThrow(NotFoundProfileException::new);
        validateExistByNickname(request.getNickname());
        Member foundMember = memberRepository.getById(memberId);

        updateProfileInfo(profile, request);
        updateMemberInfo(foundMember, profile);

        profileRepository.save(profile);
        memberRepository.save(foundMember);
    }

    private void updateProfileInfo(final Profile profile, final ProfileUpdateRequest request) {
        profile.updateNickname(request.getNickname());
        profile.updateAge(request.getAge());
        profile.updateGender(request.getGender());
        profile.updatePersonality(request.getPersonality());
        profile.updateIntroduce(request.getIntroduce());
        profile.updateImageName(request.getImageName());
        profile.updateJob(request.getJob());
        profile.updateAddressCity(request.getAddressCity());
        profile.updateAddressDistinct(request.getAddressDistrict());
        profile.updateJobVisible(request.isJobVisibility());
        profile.updateAddressVisible(request.isAddressVisibility());
        profile.updateMyImageVisibility(request.isMyImageVisibility());
    }

    public ProfileResponse findMyProfile(final Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

        return ProfileResponse.of(profile);
    }

    public ProfileOtherResponse findOtherProfile(final Long otherMemberId) {
        Profile profile = profileRepository.findByMemberId(otherMemberId)
                .orElseThrow(() -> new NotFoundProfileException("타인의 프로필을 찾을 수 없습니다."));

        return ProfileOtherResponse.of(profile);
    }
}