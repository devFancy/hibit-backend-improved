package com.hibitbackendrefactor.profile.dto.response;

import com.hibitbackendrefactor.profile.domain.*;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileOtherResponse {
    // 필수 노출 정보
    private String nickname;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;
    private String mainImg;
    private int age;

    // 선택 노출 정보(공개/비공개)
    private List<SubImage> subImages;
    private AddressCity addressCity;
    private AddressDistrict addressDistrict;
    private String job;

    private int jobVisibility;
    private int subImgVisibility;
    private int addressVisibility;
  
    public ProfileOtherResponse() {
    }

    public ProfileOtherResponse(final Profile profile) {
        this(profile.getNickname(),
                profile.getGender(),
                profile.getPersonality(),
                profile.getIntroduce(),
                profile.getMainImg(),
                profile.getSubImages(),
                profile.getAge(),
                profile.getAddressCity(),
                profile.getAddressDistrict(),
                profile.getJob(),
                profile.getJobVisible(),
                profile.getSubImgVisible(),
                profile.getAddressVisible());

        // 공개 여부에 따라 값 설정
        if (profile.getSubImgVisible() == 0 ) {
            this.subImages = null;
        }
        if (profile.getAddressVisible() == 0 ) {
            this.addressCity = null;
            this.addressDistrict = null;
        }
        if (profile.getJobVisible() == 0 ) {
            this.job = null;
        }
    }

    public ProfileOtherResponse(final String nickname, final int gender, final List<PersonalityType> personality, final String introduce, final String mainImg, final List<SubImage> subImages,
                                final int age, final AddressCity addressCity, final AddressDistrict addressDistrict, final String job,
                                final int jobVisibility, final int subImgVisibility, final int addressVisibility) {
        this.nickname = nickname;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.subImages = subImages;
        this.age = age;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.job = job;
        this.jobVisibility = jobVisibility;
        this.subImgVisibility = subImgVisibility;
        this.addressVisibility = addressVisibility;
    }
}