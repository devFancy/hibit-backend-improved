package com.hibitbackendrefactor.member.dto;


import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.SocialType;

public class MemberResponse {
    private Long id;
    private String email;
    private String displayName;
    private SocialType socialType;

    public MemberResponse(Long id, String email, String displayName, SocialType socialType) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.socialType = socialType;
    }

    public MemberResponse(final Member member) {
        this(member.getId(), member.getEmail(), member.getDisplayName(),
                member.getSocialType());
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
}
