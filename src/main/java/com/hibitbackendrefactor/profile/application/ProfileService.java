package com.hibitbackendrefactor.profile.application;

import com.hibitbackendrefactor.global.s3.S3UniqueFileName;
import com.hibitbackendrefactor.global.s3.S3UploadService;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileImage;
import com.hibitbackendrefactor.profile.domain.ProfileImageRepository;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileOtherResponse;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import com.hibitbackendrefactor.profile.exception.InvalidProfileAlreadyException;
import com.hibitbackendrefactor.profile.exception.NicknameAlreadyTakenException;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final S3UploadService s3UploadService;
    private final ProfileImageRepository profileImageRepository;

    public ProfileService(final MemberRepository memberRepository, final ProfileRepository profileRepository
            , final S3UploadService s3UploadService, final ProfileImageRepository profileImageRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
        this.s3UploadService = s3UploadService;
        this.profileImageRepository = profileImageRepository;
    }

    @Transactional
    public Long saveMyProfile(final Long memberId, final ProfileCreateRequest request, final List<MultipartFile> multipartFiles) throws IOException {
        validateExistByNickname(request.getNickname());
        if (profileRepository.existsByMemberId(memberId)) {
            throw new InvalidProfileAlreadyException("프로필이 이미 존재합니다.");
        }
        Member foundMember = memberRepository.getById(memberId);
        ProfileCreateRequest newRequest = createProfileCreateRequest(request, multipartFiles);
        Profile newProfile = profileRepository.save(newRequest.toEntity(foundMember, newRequest));

        // 저장한 프로필ID를 가져와서 프로필 이미지 저장
        saveProfileImages(newProfile.getId(), newRequest.getImages());
        updateMemberInfo(foundMember, newProfile);
        return newProfile.getId();
    }

    private void validateExistByNickname(final String nickname) {
        if (profileRepository.existsByNickname((nickname))) {
            throw new NicknameAlreadyTakenException("이미 사용중인 닉네임입니다.");
        }
    }

    private ProfileCreateRequest createProfileCreateRequest(final ProfileCreateRequest request, final List<MultipartFile> multipartFiles) {
        return new ProfileCreateRequest(
                request.getNickname(),
                request.getAge(),
                request.getGender(),
                request.getPersonality(),
                request.getIntroduce(),
                multipartFiles,
                request.getJob(),
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.isJobVisibility(),
                request.isAddressVisibility(),
                request.isMyImageVisibility());
    }

    private void saveProfileImages(final Long profileId, final List<MultipartFile> images) throws IOException {
        List<String> savedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty())
                savedImages.add(saveProfileImage(profileId, image));
        }
    }

    private String saveProfileImage(final Long profileId, final MultipartFile image) throws IOException {
        String uniqueFileName = S3UniqueFileName.generateUniqueFileName(image.getOriginalFilename());
        String savedImageUrl = s3UploadService.saveFile(image, uniqueFileName);

        ProfileImage profileImage = new ProfileImage(profileRepository.getById(profileId), savedImageUrl);
        profileImageRepository.save(profileImage);
        return savedImageUrl;
    }

    private void updateMemberInfo(final Member member, final Profile profile) {
        member.updateDisplayName(profile.getNickname());
        member.updateIsprofile();

        // ProfileImageRepository를 사용하여 프로필 이미지를 조회
        List<ProfileImage> profileImages =  profileImageRepository.findByProfileId(profile.getId());

        // 프로필 이미지가 존재하면 첫 번째 이미지를 사용하여 회원 정보 업데이트
        if (!profileImages.isEmpty()) {
            ProfileImage firstImage = profileImages.get(0);
            member.updateMainImage(firstImage.getImageUrl());
        }

        memberRepository.save(member);
    }

    @Transactional
    public void updateProfile(final Long memberId, final Long profileId, final ProfileUpdateRequest request) throws IOException {
        validateExistByNickname(request.getNickname());
        Member foundMember = memberRepository.getById(memberId);

        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(NotFoundProfileException::new);

        deleteProfileImageByProfileId(profileId);
        profileImageRepository.deleteByProfileId(profileId);

        profile.updateNickname(request.getNickname());
        profile.updateAge(request.getAge());
        profile.updateGender(request.getGender());
        profile.updatePersonality(request.getPersonality());
        profile.updateIntroduce(request.getIntroduce());
        profile.updateJob(request.getJob());
        profile.updateAddressCity(request.getAddressCity());
        profile.updateAddressDistinct(request.getAddressDistrict());
        profile.updateJobVisible(request.isJobVisibility());
        profile.updateAddressVisible(request.isAddressVisibility());
        profile.updateMyImageVisibility(request.isMyImageVisibility());
        saveProfileImages(profileId, request.getImages());
        profileRepository.save(profile);

        updateMemberInfo(foundMember, profile);
        memberRepository.save(foundMember);
    }

    private void deleteProfileImageByProfileId(final Long profileId) throws MalformedURLException {
        List<ProfileImage> profileImages = profileImageRepository.findByProfileId(profileId);
        for (ProfileImage profileImage : profileImages) {
            s3UploadService.deleteFile(profileImage.getImageUrl());
        }
    }

    public ProfileResponse findMyProfile(final Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

        List<String> imageUrls = profileImageRepository.findByProfile(profile)
                .stream()
                .map(ProfileImage::getImageUrl)
                .collect(Collectors.toList());

        return ProfileResponse.of(profile, imageUrls);
    }

    public ProfileOtherResponse findOtherProfile(final Long otherMemberId) {
        Profile profile = profileRepository.findByMemberId(otherMemberId)
                .orElseThrow(() -> new NotFoundProfileException("타인의 프로필을 찾을 수 없습니다."));

        List<String> imageUrls = profileImageRepository.findByProfile(profile)
                .stream()
                .map(ProfileImage::getImageUrl)
                .collect(Collectors.toList());

        return ProfileOtherResponse.of(profile, imageUrls);
    }
}