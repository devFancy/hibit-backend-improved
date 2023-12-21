package com.hibitbackendrefactor.auth.dto;


import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.SocialType;

public class OAuthMember {

    private final String email;

    private final String displayName;
    private final String profileImageUrl;

    private final String refreshToken;


    public OAuthMember(String email, String displayName, String profileImageUrl, String refreshToken) {
        this.email = email;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

    public Member toMember() {
        return new Member(email, displayName, profileImageUrl, SocialType.GOOGLE);
    }

}