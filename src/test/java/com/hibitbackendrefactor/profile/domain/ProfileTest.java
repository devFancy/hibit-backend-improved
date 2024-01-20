package com.hibitbackendrefactor.profile.domain;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.profile.exception.InvalidIntroduceException;
import com.hibitbackendrefactor.profile.exception.InvalidNicknameException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.hibitbackendrefactor.common.fixtures.MemberFixtures.팬시;
import static com.hibitbackendrefactor.common.fixtures.ProfileFixtures.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProfileTest {

    @DisplayName("프로필을 생성한다.")
    @Test
    void 프로필을_생성한다() {
        // given
        Member 팬시 = 팬시();

        // when & then
        assertDoesNotThrow(() -> new Profile(팬시, 팬시_닉네임, 팬시_나이
                , 팬시_성별, 팬시_성격, 팬시_자기소개, 팬시_이미지
                , 팬시_직업, 팬시_사는도시, 팬시_사는지역
                , 직업_공개여부, 주소_공개여부, 이미지_공개여부));
    }

    @DisplayName("닉네임이 1자 이상 20자 이하이면 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"팬시", "fancy", "devfancy"})
    void 닉네임이_1자이상_20자_이하이면_성공한다(String nickname) {
        // given
        Member 팬시 = 팬시();

        // when & then
        assertDoesNotThrow(() -> new Profile(팬시, nickname, 팬시_나이
                , 팬시_성별, 팬시_성격, 팬시_자기소개, 팬시_이미지
                , 팬시_직업, 팬시_사는도시, 팬시_사는지역
                , 직업_공개여부, 주소_공개여부, 이미지_공개여부));

     }

    @DisplayName("닉네임이 공백이거나 20자 이상 초과하면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "일이삼사오육칠팔구십"
            + "일이삼사오육칠팔구십" + "일"})
    void 닉네임이_공백이거나_20자_이상_초과하면_예외를_던진다(final String 잘못된_닉네임) {
        // given
        Member 팬시 = 팬시();

        // when & then
        // isBlank - 문자열이 null or 비어있거나 or 공백 문자가 포함되는 경우 (Java 11)
        assertThatThrownBy(
                () -> new Profile(팬시, 잘못된_닉네임, 팬시_나이
                        , 팬시_성별, 팬시_성격, 팬시_자기소개, 팬시_이미지
                        , 팬시_직업, 팬시_사는도시, 팬시_사는지역
                        , 직업_공개여부, 주소_공개여부, 이미지_공개여부)
        ).isInstanceOf(InvalidNicknameException.class);
    }

    @DisplayName("자기소개의 길이가 1자 이상 200자 이하인 경우 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"일", "안녕하세요", "안녕하세요. 저는 개발자 팬시입니다. 취미는 운동, 독서입니다. 잘부탁드립니다."})
    void 자기소개의_길이가_1자_이상_200자_이하인_경우_성공한다(String introduce) {
        // given
        Member 팬시 = 팬시();

        // when & then
        assertDoesNotThrow(() -> new Profile(팬시, 팬시_닉네임, 팬시_나이
                , 팬시_성별, 팬시_성격, introduce, 팬시_이미지
                , 팬시_직업, 팬시_사는도시, 팬시_사는지역
                , 직업_공개여부, 주소_공개여부, 이미지_공개여부));

     }

    @DisplayName("자기소개의 길이가 0인 경우 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 자기소개의_길이가_0인_경우_예외를_던진다(final String 잘못된_자기소개_길이) {
        // given
        Member 팬시 = 팬시();

        // when & then
        assertThatThrownBy(
                () -> new Profile(팬시, 팬시_닉네임, 팬시_나이
                        , 팬시_성별, 팬시_성격, 잘못된_자기소개_길이, 팬시_이미지
                        , 팬시_직업, 팬시_사는도시, 팬시_사는지역
                        , 직업_공개여부, 주소_공개여부, 이미지_공개여부)
        ).isInstanceOf(InvalidIntroduceException.class);
    }

    @DisplayName("자기소개의 길이가 200을 초과하는 경우 예외를 던진다.")
    @Test
    void 자기소개의_길이가_200을_초과하는_경우_예외를_던진다() {
        // given
        Member 팬시 = 팬시();
        String 잘못된_자기소개_길이 = "1".repeat(201);

        // when & then
        assertThatThrownBy(
                () -> new Profile(팬시, 팬시_닉네임, 팬시_나이
                        , 팬시_성별, 팬시_성격, 잘못된_자기소개_길이, 팬시_이미지
                        , 팬시_직업, 팬시_사는도시, 팬시_사는지역
                        , 직업_공개여부, 주소_공개여부, 이미지_공개여부)
        ).isInstanceOf(InvalidIntroduceException.class);
    }

    @DisplayName("본인의 프로필에서 나이를 변경한다.")
    @Test
    void 본인의_프로필에서_나이를_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        int age = 29;

        // when
        profile.updateAge(age);

        // then
        Assertions.assertThat(profile.getAge()).isEqualTo(age);
     }

    @DisplayName("본인의 프로필에서 자기소개를 변경한다.")
    @Test
    void 본인의_프로필에서_자기소개를_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        String introduce = "안녕하세요 서버 개발자로 살아가는 팬시입니다.";

        // when
        profile.updateIntroduce(introduce);

        // then
        Assertions.assertThat(profile.getIntroduce()).isEqualTo(introduce);
    }

    @DisplayName("본인의 프로필에서 성격을 변경한다.")
    @Test
    void 본인의_프로필에서_성격을_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        PersonalityType personalityType = PersonalityType.TYPE_2;

        // when
        profile.updatePersonality(personalityType);

        // then
        Assertions.assertThat(profile.getPersonality()).isEqualTo(personalityType);
    }

    @DisplayName("본인의 프로필에서 이미지를 변경한다.")
    @Test
    void 본인의_프로필에서_이미지를_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        String imageName = "devfancy.png";

        // when
        profile.updateImageName(imageName);

        // then
        Assertions.assertThat(profile.getImageName()).isEqualTo(imageName);
    }

    @DisplayName("본인의 프로필에서 직업을 변경한다.")
    @Test
    void 본인의_프로필에서_직업을_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        String job = "서버 개발자";

        // when
        profile.updateJob(job);

        // then
        Assertions.assertThat(profile.getJob()).isEqualTo(job);
    }

    @DisplayName("본인의 프로필에서 주소인 도시를 변경한다.")
    @Test
    void 본인의_프로필에서_주소인_도시를_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        AddressCity addressCity = AddressCity.BUSAN;

        // when
        profile.updateAddressCity(addressCity);

        // then
        Assertions.assertThat(profile.getAddressCity()).isEqualTo(addressCity);
    }

    @DisplayName("본인의 프로필에서 주소인 구를 변경한다.")
    @Test
    void 본인의_프로필에서_주소인_구를_변경한다() {
        // given
        Profile profile = 팬시_프로필();
        AddressDistrict addressDistrict = AddressDistrict.BUSAN_HAEUNDAE;

        // when
        profile.updateAddressDistinct(addressDistrict);

        // then
        Assertions.assertThat(profile.getAddressDistrict()).isEqualTo(addressDistrict);
    }
}