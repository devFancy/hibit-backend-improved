package com.hibitbackendrefactor.member.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.exception.InvalidMemberException;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Entity
public class Member extends BaseEntity {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private static final int MAX_DISPLAY_NAME_LENGTH = 20;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "display_name", nullable = false)
    private String displayName; // 구글로부터 가져오는 닉네임

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isProfile;


    protected Member() {
    }

    public Member(String email, String displayName, SocialType socialType) {

        validateEmail(email);
        validateDisplayName(displayName);

        this.email = email;
        this.displayName = displayName;
        this.socialType = socialType;
    }

    private void validateEmail(final String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private void validateDisplayName(final String displayName) {
        if (displayName.isEmpty() || displayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            throw new InvalidMemberException(String.format("이름은 1자 이상 1자 %d이하여야 합니다.", MAX_DISPLAY_NAME_LENGTH));
        }
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

    public void updateDisplayName(String nickname) {
        this.displayName = nickname;
    }
    public boolean getIsprofile(){ return isProfile;}
    public void updateIsprofile(){this.isProfile=true;}
}
