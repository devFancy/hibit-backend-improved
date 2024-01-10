package com.hibitbackendrefactor.common.fixtures;

import com.hibitbackendrefactor.profile.domain.AddressCity;
import com.hibitbackendrefactor.profile.domain.AddressDistrict;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.domain.Profile;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;

public class ProfileFixtures {

    /* 팬시 프로필 생성 */
    public static final String 팬시_닉네임 = "팬시";
    public static final int 팬시_나이 = 28;
    public static final int 팬시_성별 = 0;
    public static final PersonalityType 팬시_성격 = PersonalityType.TYPE_1;
    public static final String 팬시_자기소개 = "안녕하세요. 저는 소프트웨어 개발자 팬시입니다.";
    public static final String 팬시_이미지 = "fancy.png";
    public static final String 팬시_직업 = "소프트웨어 개발자";
    public static final AddressCity 팬시_사는도시 = AddressCity.SEOUL;
    public static final AddressDistrict 팬시_사는지역 = AddressDistrict.SEOUL_GANGNAM;

    public static final boolean 직업_공개여부 = true;
    public static final boolean 주소_공개여부 = false;
    public static final boolean 이미지_공개여부 = false;

    public static Profile 팬시_프로필() {
        return Profile.builder()
                .member(팬시())
                .nickname(팬시_닉네임)
                .age(팬시_나이  )
                .gender(팬시_성별)
                .personality(팬시_성격)
                .introduce(팬시_자기소개)
                .imageName(팬시_이미지)
                .job(팬시_직업)
                .addressCity(팬시_사는도시)
                .addressDistrict(팬시_사는지역)
                .jobVisible(직업_공개여부)
                .addressVisible(주소_공개여부)
                .myImageVisibility(이미지_공개여부)
                .build();
    }
}
