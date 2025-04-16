package com.hibitbackendimproved.post.exception;

public class NotFoundTogetherActivityException extends RuntimeException {
    public NotFoundTogetherActivityException(final String message) {
        super(message);
    }

    public NotFoundTogetherActivityException() {
        this("존재하지 않는 함께 하고 싶은 활동입니다.");
    }
}
