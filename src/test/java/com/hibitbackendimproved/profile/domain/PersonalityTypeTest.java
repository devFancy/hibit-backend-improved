package com.hibitbackendimproved.profile.domain;

import com.hibitbackendimproved.profile.exception.NotFoundPersonalityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PersonalityTypeTest {

    @DisplayName("성격 유형에 해당하는 값을 가져온다.")
    @ParameterizedTest
    @EnumSource
    void 성격_유형에_해당하는_값을_가져온다(final PersonalityType personalityType) {
        // given & when & then
        assertAll(() -> {
            assertThat(PersonalityType.from(personalityType.name())).isEqualTo(personalityType);
            assertThat(PersonalityType.from(personalityType.name().toLowerCase())).isEqualTo(personalityType);
        });
    }

    @DisplayName("존재하지 않는 성격 유형인 경우 예외를 던진다.")
    @Test
    void 존재하지_성격_유형인_경우_예외를_던진다() {
        // given
        String notFoundPersonalityType = "존재하지 않는 성격유형";

        // when & then
        assertThatThrownBy(() -> PersonalityType.from(notFoundPersonalityType))
                .isInstanceOf(NotFoundPersonalityException.class);
    }
}
