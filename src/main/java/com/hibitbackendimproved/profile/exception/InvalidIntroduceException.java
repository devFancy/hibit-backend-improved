package com.hibitbackendimproved.profile.exception;

public class InvalidIntroduceException extends RuntimeException {

    public InvalidIntroduceException(final String message) {
        super(message);
    }

    public InvalidIntroduceException() {
        this("자기소개는 1자 이상 200자 이하여야 합니다.");
    }
}
