package com.hibitbackendrefactor.common.fixtures;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.SocialType;

public class MemberFixtures {

    /* 팬시 */

    public static final String 팬시_이메일 = "fancy@gmail.com";
    public static final String 팬시_닉네임 = "팬시";
    public static final SocialType 소셜로그인유형 = SocialType.GOOGLE;
    public static Member 팬시() {
        return Member.builder()
                .email(팬시_이메일)
                .displayName(팬시_닉네임)
                .socialType(소셜로그인유형)
                .build();
    }
}