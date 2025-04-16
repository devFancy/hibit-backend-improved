package com.hibitbackendimproved.profile.presentation;


import com.hibitbackendimproved.auth.dto.LoginMember;
import com.hibitbackendimproved.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendimproved.support.ApiResponse;
import com.hibitbackendimproved.profile.application.ProfileService;
import com.hibitbackendimproved.profile.domain.PersonalityType;
import com.hibitbackendimproved.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendimproved.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendimproved.profile.dto.response.ProfileOtherResponse;
import com.hibitbackendimproved.profile.dto.response.ProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/api/profiles/new")
    public ResponseEntity<ApiResponse<ProfileResponse>> saveMyProfile(@AuthenticationPrincipal final LoginMember loginMember,
                                                          @Valid @RequestBody final ProfileCreateRequest request) {
        ProfileResponse profileResponse = profileService.save(loginMember.getId(), request);
        ApiResponse<ProfileResponse> apiResponse = ApiResponse.created(profileResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/api/profiles/personalities")
    public ResponseEntity<ApiResponse<List<PersonalityType>>> getAvailablePersonalities() {
        List<PersonalityType> personalities = Arrays.asList(PersonalityType.values());
        ApiResponse<List<PersonalityType>> apiResponse = ApiResponse.ok(personalities);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/api/profiles/me")
    public ResponseEntity<ApiResponse<ProfileResponse>> findMyProfile(@AuthenticationPrincipal final LoginMember loginMember) {
        ProfileResponse profileResponse = profileService.findMyProfile(loginMember.getId());
        ApiResponse<ProfileResponse> apiResponse = ApiResponse.ok(profileResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/api/profiles/other/{id}")
    public ResponseEntity<ApiResponse<ProfileOtherResponse>> findOtherProfile(@AuthenticationPrincipal final LoginMember loginMember,
                                                                 @PathVariable(name = "id") final Long otherMemberId) {
        ProfileOtherResponse profileOtherResponse = profileService.findOtherProfile(otherMemberId);
        ApiResponse<ProfileOtherResponse> apiResponse = ApiResponse.ok(profileOtherResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/api/profiles/me")
    public ResponseEntity<ApiResponse<Void>> update(@AuthenticationPrincipal final LoginMember loginMember,
                                       @Valid @RequestBody final ProfileUpdateRequest request) {
        profileService.update(loginMember.getId(), request);
        ApiResponse<Void> apiResponse = ApiResponse.noContent();
        return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
    }
}
