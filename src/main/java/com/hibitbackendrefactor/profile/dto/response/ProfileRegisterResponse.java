package com.hibitbackendrefactor.profile.dto.response;

import com.hibitbackendrefactor.profile.domain.*;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileRegisterResponse {

    private Long id;
    private String nickname;
    private int age;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;
    private String mainImg;
    private List<SubImage> subImages;
    private String job;
    private AddressCity addressCity;
    private AddressDistrict addressDistrict;
    private int jobVisibility;
    private int subImgVisibility;
    private int addressVisibility;

    public ProfileRegisterResponse(final Long id, final String nickname, final int age, final int gender
            , final List<PersonalityType> personality, final String introduce
            , final String mainImg, final List<SubImage> subImages, final String job
            , final AddressCity addressCity, final AddressDistrict addressDistrict
            , final int jobVisibility, final int subImgVisibility, final int addressVisibility) {
        this.id = id;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.subImages = subImages;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.jobVisibility = jobVisibility;
        this.subImgVisibility = subImgVisibility;
        this.addressVisibility = addressVisibility;
    }

    public ProfileRegisterResponse(final Profile saveProfile) {
        this.id = saveProfile.getId();
        this.nickname = saveProfile.getNickname();
        this.age = saveProfile.getAge();
        this.gender = saveProfile.getGender();
        this.personality = saveProfile.getPersonality();
        this.introduce = saveProfile.getIntroduce();
        this.mainImg = saveProfile.getMainImg();
        this.subImages = saveProfile.getSubImages();
        this.job = saveProfile.getJob();
        this.addressCity = saveProfile.getAddressCity();
        this.addressDistrict = saveProfile.getAddressDistrict();
        this.jobVisibility = saveProfile.getJobVisible();
        this.subImgVisibility = saveProfile.getSubImgVisible();
        this.addressVisibility = saveProfile.getAddressVisible();
    }
}
