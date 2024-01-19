package com.hibitbackendrefactor.common.fixtures;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.SocialType;
import com.hibitbackendrefactor.member.dto.MemberResponse;

public class MemberFixtures {

    /* 팬시 */
    public static final String 팬시_이메일 = "fancy@gmail.com";
    public static final String 팬시_닉네임 = "팬시";
    public static final SocialType 소셜로그인유형 = SocialType.GOOGLE;

    public static final boolean 팬시_프로필여부 = true;

    public static final MemberResponse 팬시_응답 = new MemberResponse(1L, 팬시_이메일, 팬시_닉네임, 소셜로그인유형 , 팬시_프로필여부);

    /* 브루스 */
    public static final String 브루스_이메일 = "fancy@gmail.com";
    public static final String 브루스_닉네임 = "팬시";

    /* 데이브 */
    public static final String 데이브_이메일 = "fancy@gmail.com";
    public static final String 데이브_닉네임 = "팬시";


    public static Member 팬시() {
        return Member.builder()
                .email(팬시_이메일)
                .displayName(팬시_닉네임)
                .socialType(소셜로그인유형)
                .build();
    }

    public static Member 브루스() {
        return Member.builder()
                .email(브루스_이메일)
                .displayName(브루스_닉네임)
                .socialType(소셜로그인유형)
                .build();
    }

    public static Member 데이브() {
        return Member.builder()
                .email(데이브_이메일)
                .displayName(데이브_닉네임)
                .socialType(소셜로그인유형)
                .build();
    }
}