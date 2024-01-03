package com.hibitbackendrefactor.profile.dto.request;

import com.hibitbackendrefactor.profile.domain.AddressCity;
import com.hibitbackendrefactor.profile.domain.AddressDistrict;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.domain.SubImage;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class ProfileUpdateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    private String nickname;

    @NotNull(message = "공백일 수 없습니다.")
    private int age;

    @NotNull(message = "공백일 수 없습니다.")
    private int gender;

    @NotBlank(message = "공백일 수 없습니다.")
    private List<PersonalityType> personality;

    @NotBlank(message = "공백일 수 없습니다.")
    private String introduce;

    @NotBlank(message = "공백일 수 없습니다.")
    private String mainImg;

    private List<SubImage> subImages;

    private String job;

    private AddressCity addressCity;

    private AddressDistrict addressDistrict;

    private int jobVisibility;

    private int subImgVisibility;

    private int addressVisibility;

    public ProfileUpdateRequest() {
    }

    public ProfileUpdateRequest(final String nickname, final int age, final int gender, final List<PersonalityType> personality,
                                final String introduce,
                                final String mainImg, final List<SubImage> subImages, final String job, final AddressCity addressCity, final AddressDistrict addressDistrict,
                                final int jobVisibility, final int subImgVisibility, final int addressVisibility) {
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
}
