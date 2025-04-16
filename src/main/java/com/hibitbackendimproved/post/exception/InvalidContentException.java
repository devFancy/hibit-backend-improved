package com.hibitbackendimproved.post.exception;

public class InvalidContentException extends RuntimeException {

    private static final String MESSAGE = "본문은 1자 이상 200자 이하여야 합니다.";

    public InvalidContentException() {
        super(MESSAGE);
    }
}
