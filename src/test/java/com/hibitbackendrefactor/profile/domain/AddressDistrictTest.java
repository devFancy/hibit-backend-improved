package com.hibitbackendrefactor.profile.domain;

import com.hibitbackendrefactor.profile.exception.NotFoundAddressDistrictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AddressDistrictTest {

    @DisplayName("주소에서 구에 해당하는 값을 가져온다.")
    @ParameterizedTest
    @EnumSource
    void 주소에서_구에_해당하는_값을_가져온다(final AddressDistrict addressDistrict) {
        // given & when & then
        assertAll(() -> {
            assertThat(AddressDistrict.from(addressDistrict.name())).isEqualTo(addressDistrict.getCityName());
            assertThat(AddressDistrict.from(addressDistrict.name().toLowerCase())).isEqualTo(addressDistrict.getCityName());
        });
    }

    @DisplayName("존재하지 않는 도시인 경우 예외를 던진다")
    @Test
    void 존재하지_않는_도시인_경우_예외를_던진다() {
        // given
        String notFoundAddressDistrictName = "존재하지 않는 도시";

        // when & then
        assertThatThrownBy(() -> AddressDistrict.from(notFoundAddressDistrictName))
                .isInstanceOf(NotFoundAddressDistrictException.class);
    }

}