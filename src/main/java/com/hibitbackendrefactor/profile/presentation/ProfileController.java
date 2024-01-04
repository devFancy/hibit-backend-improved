package com.hibitbackendrefactor.profile.presentation;


import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendrefactor.profile.application.ProfileService;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendrefactor.profile.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Tag(name = "profile", description = "프로필")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @Operation(description = "본인 프로필을 등록한다.")
    public ResponseEntity<Void> saveMyProfile(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                                               @Valid @RequestPart final ProfileCreateRequest request,
                                                               @RequestPart final List<MultipartFile> multipartFiles) throws IOException {
        Long profileId = profileService.saveMyProfile(loginMember.getId(), request, multipartFiles);
        return ResponseEntity.created(URI.create("/api/profiles/" + profileId)).build();
    }

    @GetMapping("/personalities")
    @Operation(summary = "/personalities", description = "사용자에게 선택할 수 있는 성격 목록을 반환한다.")
    public ResponseEntity<List<PersonalityType>> getAvailablePersonalities() {
        List<PersonalityType> personalities = Arrays.asList(PersonalityType.values());
        return ResponseEntity.ok(personalities);
    }

    @GetMapping("/me")
    @Operation(summary = "/me", description = "본인 프로필을 조회한다.")
    public ResponseEntity<ProfileResponse> findMyProfile(@AuthenticationPrincipal final LoginMember loginMember) {
        ProfileResponse response = profileService.findMyProfile(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other/{id}")
    @Operation(summary = "other/2", description = "타인 프로필을 조회한다.")
    public ResponseEntity<ProfileOtherResponse> findOtherProfile(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                                                           @PathVariable(name = "id") final Long otherMemberId) {
        ProfileOtherResponse response = profileService.findOtherProfile(otherMemberId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/{id}")
    @Operation(summary = "/me/1", description = "본인 프로필을 수정한다.")
    public ResponseEntity<Void> update(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                       @PathVariable(name = "id") final Long profileId,
                                       @Valid @RequestPart final ProfileUpdateRequest request,
                                       @RequestPart final List<MultipartFile> multipartFiles) throws IOException {
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(request.getNickname(), request.getAge(), request.getGender()
                , request.getPersonality(), request.getIntroduce(), multipartFiles
                , request.getJob(), request.getAddressCity(), request.getAddressDistrict()
                , request.isJobVisibility(), request.isAddressVisibility(), request.isMyImageVisibility());
        profileService.updateProfile(loginMember.getId(), profileId, profileUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}