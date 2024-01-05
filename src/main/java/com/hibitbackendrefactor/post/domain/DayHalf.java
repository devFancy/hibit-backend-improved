package com.hibitbackendrefactor.post.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayHalf {
    AM("오전"),
    PM("오후");

    private final String text;
}
