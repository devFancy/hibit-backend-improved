package com.hibitbackendrefactor.profile.presentation;


import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.auth.presentation.AuthenticationPrincipal;
import com.hibitbackendrefactor.profile.application.ProfileService;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.dto.request.ProfileCreateRequest;
import com.hibitbackendrefactor.profile.dto.request.ProfileUpdateRequest;
import com.hibitbackendrefactor.profile.dto.response.ProfileOtherResponse;
import com.hibitbackendrefactor.profile.dto.response.ProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/api/profiles/new")
    public ResponseEntity<Void> saveMyProfile(@AuthenticationPrincipal final LoginMember loginMember,
                                              @Valid @RequestBody final ProfileCreateRequest request) {
        Long profileId = profileService.save(loginMember.getId(), request);
        return ResponseEntity.created(URI.create("/api/profiles/" + profileId)).build();
    }

    @GetMapping("/api/profiles/personalities")
    public ResponseEntity<List<PersonalityType>> getAvailablePersonalities() {
        List<PersonalityType> personalities = Arrays.asList(PersonalityType.values());
        return ResponseEntity.ok(personalities);
    }

    @GetMapping("/api/profiles/me")
    public ResponseEntity<ProfileResponse> findMyProfile(@AuthenticationPrincipal final LoginMember loginMember) {
        ProfileResponse response = profileService.findMyProfile(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/profiles/other/{id}")
    public ResponseEntity<ProfileOtherResponse> findOtherProfile(@AuthenticationPrincipal final LoginMember loginMember,
                                                                 @PathVariable(name = "id") final Long otherMemberId) {
        ProfileOtherResponse response = profileService.findOtherProfile(otherMemberId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/profiles/me")
    public ResponseEntity<Void> update(@AuthenticationPrincipal final LoginMember loginMember,
                                       @Valid @RequestBody final ProfileUpdateRequest request) {
        profileService.update(loginMember.getId(), request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}