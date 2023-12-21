package com.hibitbackendrefactor.profile.exception;

public class NotFoundProfileException extends RuntimeException {

    public NotFoundProfileException(final String message) {
        super(message);
    }

    public NotFoundProfileException() {
        this("존재하지 않는 프로필입니다.");
    }
}
