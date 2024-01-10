package com.hibitbackendrefactor.profile.exception;

public class InvalidPersonalityException extends RuntimeException {
    public InvalidPersonalityException(final String message) {
        super(message);
    }

    public InvalidPersonalityException() {
        this("성격은 1개만 선택할 수 있습니다.");
    }

}
