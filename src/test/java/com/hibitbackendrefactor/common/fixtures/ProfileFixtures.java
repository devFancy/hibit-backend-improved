package com.hibitbackendrefactor.common.fixtures;

import com.hibitbackendrefactor.profile.domain.AddressCity;
import com.hibitbackendrefactor.profile.domain.AddressDistrict;
import com.hibitbackendrefactor.profile.domain.PersonalityType;

import java.util.Arrays;
import java.util.List;

public class ProfileFixtures {

    /* 팬시 프로필 생성 */
    public static final String 팬시_닉네임 = "팬시";
    public static final int 팬시_나이 = 28;
    public static final int 팬시_성별 = 0;
    public static final List<PersonalityType> 팬시_성격들 = Arrays.asList(
            PersonalityType.TYPE_1,
            PersonalityType.TYPE_2,
            PersonalityType.TYPE_3,
            PersonalityType.TYPE_4,
            PersonalityType.TYPE_5
    );
    public static final String 팬시_자기소개 = "안녕하세요. 저는 소프트웨어 개발자 팬시입니다.";
    public static final String 팬시_직업 = "소프트웨어 개발자";
    public static final AddressCity 팬시_사는도시 = AddressCity.SEOUL;
    public static final AddressDistrict 팬시_사는지역 = AddressDistrict.SEOUL_GANGNAM;

    public static final boolean 직업_공개여부 = true;
    public static final boolean 주소_공개여부 = false;
    public static final boolean 이미지_공개여부 = false;
}
