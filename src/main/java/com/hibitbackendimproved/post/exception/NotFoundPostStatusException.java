package com.hibitbackendimproved.post.exception;

public class NotFoundPostStatusException extends RuntimeException {
    public NotFoundPostStatusException(final String message) {
        super(message);
    }

    public NotFoundPostStatusException() {
        this("존재하지 않는 모집 상태입니다.");
    }
}
