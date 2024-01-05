package com.hibitbackendrefactor.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    HOLDING("모집 중"),
    CANCELLED("모집 취소"),
    COMPLETED("모집 완료");

    private final String text;
}
