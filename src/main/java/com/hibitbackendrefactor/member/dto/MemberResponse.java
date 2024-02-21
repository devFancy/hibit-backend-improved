package com.hibitbackendrefactor.member.dto;


import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.SocialType;

public class MemberResponse {
    private Long id;
    private String email;
    private String displayName;
    private SocialType socialType;
    private boolean isProfile;

    private MemberResponse() {
    }

    public MemberResponse(final Long id, final String email, final String displayName, final SocialType socialType, final boolean isProfile) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.socialType = socialType;
        this.isProfile = isProfile;
    }

    public MemberResponse(final Member member) {
        this(member.getId(), member.getEmail(), member.getDisplayName(), member.getSocialType(), member.getIsprofile());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SocialType getSocialType() {
        return socialType;
    }

    public boolean isProfile() {
        return isProfile;
    }
}
