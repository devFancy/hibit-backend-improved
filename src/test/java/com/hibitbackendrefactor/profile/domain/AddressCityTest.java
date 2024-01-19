package com.hibitbackendrefactor.profile.domain;

import com.hibitbackendrefactor.profile.exception.NotFoundAddressCityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AddressCityTest {

    @DisplayName("주소에서 도시에 해당하는 값을 가져온다.")
    @ParameterizedTest
    @EnumSource
    void 주소에서_도시에_해당하는_값을_가져온다(final AddressCity addressCity) {
        // given & when & then
        assertAll(() -> {
            assertThat(AddressCity.from(addressCity.name())).isEqualTo(addressCity);
            assertThat(AddressCity.from(addressCity.name().toLowerCase())).isEqualTo(addressCity);
        });
     }

     @DisplayName("존재하지 않는 도시인 경우 예외를 던진다")
     @Test
     void 존재하지_않는_도시인_경우_예외를_던진다() {
         // given
         String notFoundAddressCityName = "존재하지 않는 도시";

         // when & then
         assertThatThrownBy(() -> AddressCity.from(notFoundAddressCityName))
                 .isInstanceOf(NotFoundAddressCityException.class);
      }
}