package com.hibitbackendrefactor.post.domain;


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
}
