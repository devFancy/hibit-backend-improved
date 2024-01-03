package com.hibitbackendrefactor.profile.application;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import com.hibitbackendrefactor.profile.dto.request.ProfileRegisterRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileOtherResponse;
import com.hibitbackendrefactor.profile.dto.response.ProfileRegisterResponse;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import com.hibitbackendrefactor.profile.dto.response.ProfilesResponse;
import com.hibitbackendrefactor.profile.exception.NicknameAlreadyTakenException;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(final MemberRepository memberRepository, final ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    public ProfileRegisterResponse saveMyProfile(final Long memberId, final ProfileRegisterRequest request) {
        Member foundMember = memberRepository.getById(memberId);

        if (profileRepository.existsByNickname(request.getNickname())) {
            throw new NicknameAlreadyTakenException("이미 사용 중인 닉네임입니다.");
        }

        Profile newProfile = createProfile(foundMember, request);
        updateMemberInfo(foundMember, newProfile);

        return new ProfileRegisterResponse(newProfile);
    }

    private Profile createProfile(final Member member, final ProfileRegisterRequest request) {
        return profileRepository.save(Profile.builder()
                .member(member)
                .nickname(request.getNickname())
                .age(request.getAge())
                .gender(request.getGender())
                .personality(request.getPersonality())
                .introduce(request.getIntroduce())
                .mainImg(request.getMainImg())
                .subImages(request.getSubImages())
                .job(request.getJob())
                .addressCity(request.getAddressCity())
                .addressDistrict(request.getAddressDistrict())
                .jobVisible(request.getJobVisibility())
                .subImgVisible(request.getSubImgVisibility())
                .addressVisible(request.getAddressVisibility())
                .build());
    }

    private void updateMemberInfo(final Member member, final Profile profile) {
        member.updateDisplayName(profile.getNickname());
        member.updateIsprofile();
        memberRepository.save(member);
    }

    public ProfilesResponse findProfilesByIdAndMemberId() {
        List<ProfileResponse> profileResponses = profileRepository.findAll()
                .stream()
                .map(ProfileResponse::new)
                .collect(Collectors.toList());
        return new ProfilesResponse(profileResponses);
    }

    public void updateProfile(final Long memberId, final ProfileUpdateRequest request) {

        Member member = memberRepository.getById(memberId);

        member.updateDisplayName(request.getNickname());
        memberRepository.save(member);

        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

        profile.updateNickname(request.getNickname());
        profile.updateAge(request.getAge());
        profile.updateGender(request.getGender());
        profile.updatePersonality(request.getPersonality());
        profile.updateIntroduce(request.getIntroduce());
        profile.updateMainImg(request.getMainImg());
        profile.updateSubImages(request.getSubImages());
        profile.updateJob(request.getJob());
        profile.updateAddressCity(request.getAddressCity());
        profile.updateAddressDistinct(request.getAddressDistrict());
        profile.updateJobVisible(request.getJobVisibility());
        profile.updateSubImgVisible(request.getSubImgVisibility());
        profile.updateAddressVisible(request.getAddressVisibility());
        profileRepository.save(profile);
    }

    public ProfileResponse findProfileByMemberId(final Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

        return new ProfileResponse(profile);
    }

    public ProfileOtherResponse findOtherProfileByMemberId(final Long otherMemberId) {
        Profile profile = profileRepository.findByMemberId(otherMemberId)
                .orElseThrow(() -> new NotFoundProfileException("타인의 프로필을 찾을 수 없습니다."));

        return new ProfileOtherResponse(profile);
    }

    public void validateUniqueNickname(final String nickname) {
        if (profileRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyTakenException("이미 사용중인 닉네임 입니다.");
        }
    }
}