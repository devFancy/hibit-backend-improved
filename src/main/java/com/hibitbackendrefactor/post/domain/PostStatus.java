package com.hibitbackendrefactor.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    Holding("모집 중"),
    CANCELLED("모집 취소"),
    Completed("모집 완료");

    private final String text;
}
