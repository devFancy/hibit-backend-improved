package com.hibitbackendimproved.profile.domain;


import com.hibitbackendimproved.profile.exception.NotFoundAddressCityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressCity {

    SEOUL("서울특별시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    INCHEON("인천광역시"),
    GWANGJU("광주광역시"),
    DAEJEON("대전광역시"),
    ULSAN("울산광역시"),
    SEJONG("세종시"),
    GANGWON("강원도"),
    GYEONGGI("경기도"),
    CHUNGBUK("충청북도"),
    CHUNGNAM("충청남도"),
    JEONBUK("전라북도"),
    JEONNAM("전라남도"),
    GYEONGBUK("경상북도"),
    GYEONGNAM("경상남도"),
    JEJU("제주특별시");

    private final String text;

    public static AddressCity from(final String value) {
        try {
            return AddressCity.valueOf(value.toUpperCase());
        } catch (final IllegalArgumentException e) {
            throw new NotFoundAddressCityException("(" + value + ")는 존재하지 않는 도시입니다.");
        }
    }
}
