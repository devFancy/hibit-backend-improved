package com.hibitbackendrefactor.profile.domain;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.profile.exception.InvalidIntroduceException;
import com.hibitbackendrefactor.profile.exception.InvalidNicknameException;
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


}