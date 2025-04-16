package com.hibitbackendimproved.post.domain;


import com.hibitbackendimproved.post.exception.NotFoundTogetherActivityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TogetherActivity {
    EAT("맛집 가기"),
    CAFE("카페 가기"),
    ONLY("전시만 보기"),
    LATER("만나서 정해요!");

    private final String text;

    public static TogetherActivity from(final String value) {
        try {
            return TogetherActivity.valueOf(value.toUpperCase());
        } catch (final IllegalArgumentException e) {
            throw new NotFoundTogetherActivityException("(" + value + ")는 존재하지 않는 함께 하고 싶은 활동입니다.");
        }
    }
}
