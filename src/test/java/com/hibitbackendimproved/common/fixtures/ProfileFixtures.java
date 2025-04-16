package com.hibitbackendimproved.common.fixtures;

import com.hibitbackendimproved.member.domain.Member;
import com.hibitbackendimproved.profile.domain.AddressCity;
import com.hibitbackendimproved.profile.domain.AddressDistrict;
import com.hibitbackendimproved.profile.domain.PersonalityType;
import com.hibitbackendimproved.profile.domain.Profile;
import com.hibitbackendimproved.profile.dto.response.ProfileOtherResponse;

import static com.hibitbackendimproved.common.fixtures.MemberFixtures.팬시;

public class ProfileFixtures {

    /* 팬시 프로필 생성 */
    public static final String 팬시_닉네임 = "팬시";
    public static final int 팬시_나이 = 28;
    public static final int 팬시_성별 = 0;
    public static final PersonalityType 팬시_성격 = PersonalityType.TYPE_1;
    public static final String 팬시_자기소개 = "안녕하세요. 저는 백엔드 개발자 팬시입니다.";
    public static final String 팬시_이미지 = "fancy.png";
    public static final String 팬시_직업 = "백엔드 개발자";
    public static final AddressCity 팬시_사는도시 = AddressCity.SEOUL;
    public static final AddressDistrict 팬시_사는지역 = AddressDistrict.SEOUL_GANGNAM;

    /* 팬시 프로필 수정 */
    public static final String 팬시_닉네임2 = "팬시2";
    public static final int 팬시_나이2 = 28;
    public static final int 팬시_성별2 = 0;
    public static final PersonalityType 팬시_성격2 = PersonalityType.TYPE_2;
    public static final String 팬시_자기소개2 = "안녕하세요. 저는 백엔드 개발자 팬시2입니다.";
    public static final String 팬시_이미지2 = "fancy2.png";
    public static final String 팬시_직업2 = "백엔드 개발자2";
    public static final AddressCity 팬시_사는도시2 = AddressCity.SEOUL;
    public static final AddressDistrict 팬시_사는지역2 = AddressDistrict.SEOUL_GANGNAM;

    public static final boolean 직업_공개여부 = true;
    public static final boolean 주소_공개여부 = false;
    public static final boolean 이미지_공개여부 = false;

    /* 브루스 프로필 생성 */
    public static final String 브루스_닉네임 = "브루스";
    public static final int 브루스_나이 = 30;
    public static final int 브루스_성별 = 1;
    public static final PersonalityType 브루스_성격 = PersonalityType.TYPE_2;
    public static final String 브루스_자기소개 = "안녕하세요. 저는 프론트엔드 개발자 브루스입니다.";
    public static final String 브루스_이미지 = "bruce.png";
    public static final String 브루스_직업 = "프론트엔드 개발자";
    public static final AddressCity 브루스_사는도시 = AddressCity.BUSAN;
    public static final AddressDistrict 브루스_사는지역 = AddressDistrict.BUSAN_HAEUNDAE;

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
    public static Profile 팬시_프로필(final Member 팬시) {
        return Profile.builder()
                .member(팬시)
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
    public static ProfileOtherResponse 타인_프로필_조회_응답() {
        return ProfileOtherResponse.builder()
                .nickname(브루스_닉네임)
                .age(브루스_나이  )
                .gender(브루스_성별)
                .personality(브루스_성격)
                .introduce(브루스_자기소개)
                .imageName(브루스_이미지)
                .job(브루스_직업)
                .addressCity(브루스_사는도시)
                .addressDistrict(브루스_사는지역)
                .jobVisibility(직업_공개여부)
                .addressVisibility(주소_공개여부)
                .myImageVisibility(이미지_공개여부)
                .build();
    }
}
