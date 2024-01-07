package com.hibitbackendrefactor.profile.domain;


import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.profile.exception.InvalidNicknameException;
import com.hibitbackendrefactor.profile.exception.InvalidPersonalityException;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class Profile extends BaseEntity {

    private static final int MAX_NICK_NAME_LENGTH = 20;
    private static final int MAX_PERSONALITY_COUNT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "nickname", length = 20, unique = true)
    private String nickname;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private int gender;

    @Column(name = "personality")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PersonalityType.class)
    private List<PersonalityType> personality;

    @Column(name = "introduce", length = 200)
    private String introduce;

    @Column(name = "job", length = 50)
    private String job;

    @Column(name = "address_city")
    @Enumerated(EnumType.STRING)
    private AddressCity addressCity;

    @Column(name = "address_distinct")
    @Enumerated(EnumType.STRING)
    private AddressDistrict addressDistrict;

    @Column(name = "job_visible")
    private boolean jobVisible;

    @Column(name = "address_visible")
    private boolean addressVisible;

    @Column(name = "my_image_visibility")
    private boolean myImageVisibility;

    protected Profile() {
    }

    @Builder
    public Profile(final Member member, final String nickname, final int age
            , final int gender, final List<PersonalityType> personality, final String introduce
            , final String job, final AddressCity addressCity, final AddressDistrict addressDistrict
            , final boolean jobVisible, final boolean addressVisible, final boolean myImageVisibility) {
        validateNickName(nickname);
        validatePersonality(personality);
        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.jobVisible = jobVisible;
        this.addressVisible = addressVisible;
        this.myImageVisibility = myImageVisibility;
    }

    private void validateNickName(final String nickname) {
        if (nickname.isEmpty() || nickname.length() > MAX_NICK_NAME_LENGTH) {
            throw new InvalidNicknameException(String.format("이름은 1자 이상 1자 %d 이하여야 합니다.", MAX_NICK_NAME_LENGTH));
        }
    }

    private void validatePersonality(final List<PersonalityType> personality) {
        if (personality.isEmpty() || personality.size() > MAX_PERSONALITY_COUNT) {
            throw new InvalidPersonalityException(String.format("성격은 최대 %d개 입니다.", MAX_PERSONALITY_COUNT));
        }
    }

    public void updateNickname(final String nickname) {
        validateNickName(nickname);
        this.nickname = nickname;
    }

    public void updateAge(final int age) {
        this.age = age;
    }

    public void updateGender(final int gender) {
        this.gender = gender;
    }

    public void updatePersonality(final List<PersonalityType> personality) {
        validatePersonality(personality);
        this.personality = personality;
    }

    public void updateIntroduce(final String introduce) {
        this.introduce = introduce;
    }

    public void updateJob(final String job) {
        this.job = job;
    }

    public void updateAddressCity(final AddressCity addressCity) {
        this.addressCity = addressCity;
    }

    public void updateAddressDistinct(final AddressDistrict addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public void updateJobVisible(final boolean jobVisible) {
        this.jobVisible = jobVisible;
    }

    public void updateAddressVisible(final boolean addressVisible) {
        this.addressVisible = addressVisible;
    }

    public void updateMyImageVisibility(final boolean myImageVisibility) {
        this.myImageVisibility = myImageVisibility;
    }
}
