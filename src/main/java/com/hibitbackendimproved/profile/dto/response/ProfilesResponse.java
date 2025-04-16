package com.hibitbackendimproved.profile.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfilesResponse {

    private List<ProfileResponse> profiles;

    public ProfilesResponse(final List<ProfileResponse> profiles) {
        this.profiles = profiles;
    }
}
