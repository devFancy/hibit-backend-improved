package com.hibitbackendrefactor.profile.dto.response;

import com.hibitbackendrefactor.profile.domain.AddressCity;
import com.hibitbackendrefactor.profile.domain.AddressDistrict;
import com.hibitbackendrefactor.profile.domain.PersonalityType;
import com.hibitbackendrefactor.profile.domain.Profile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileOtherResponse {
    private String nickname;
    private int age;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;

    private String job;
    private AddressCity addressCity;
    private AddressDistrict addressDistrict;
    private List<String> imageUrl;
    private boolean jobVisibility;
    private boolean addressVisibility;
    private boolean myImageVisibility;

    @Builder
    public ProfileOtherResponse(final String nickname, final int age, final int gender, final List<PersonalityType> personality, String introduce
            , final String job, final AddressCity addressCity, final AddressDistrict addressDistrict, final List<String> imageUrl
            , final boolean jobVisibility, final boolean addressVisibility, final boolean myImageVisibility) {
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.imageUrl = imageUrl;
        this.jobVisibility = jobVisibility;
        this.addressVisibility = addressVisibility;
        this.myImageVisibility = myImageVisibility;
    }

    public static ProfileOtherResponse of(final Profile profile, final List<String> imageUrls) {
        return ProfileOtherResponse.builder()
                .nickname(profile.getNickname())
                .age(profile.getAge())
                .gender(profile.getGender())
                .personality(profile.getPersonality())
                .introduce(profile.getIntroduce())
                .job(profile.getJob())
                .addressCity(profile.getAddressCity())
                .addressDistrict(profile.getAddressDistrict())
                .imageUrl(imageUrls)
                .jobVisibility(profile.isJobVisible())
                .addressVisibility(profile.isAddressVisible())
                .myImageVisibility(profile.isMyImageVisibility())
                .build();

    }
}
