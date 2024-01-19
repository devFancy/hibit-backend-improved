package com.hibitbackendrefactor.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidProfileAlreadyException extends RuntimeException {
    public InvalidProfileAlreadyException(final String message) {
        super(message);
    }

    public InvalidProfileAlreadyException() {
        this("프로필이 이미 존재합니다.");
    }
}
