package com.hibitbackendimproved.post.domain;

import com.hibitbackendimproved.post.exception.NotFoundPostStatusException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    HOLDING("모집 중"),
    CANCELLED("모집 취소"),
    COMPLETED("모집 완료");

    private final String text;

    public static PostStatus from(final String value) {
        try {
            return PostStatus.valueOf(value.toUpperCase());
        } catch (final IllegalArgumentException e) {
            throw new NotFoundPostStatusException("(" + value + ")는 존재하지 않는 모집 상태입니다.");
        }
    }
}
