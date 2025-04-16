package com.hibitbackendimproved.post.exception;

public class InvalidTitleException extends RuntimeException {

    private static final String MESSAGE = "제목은 1자 이상 30자 이하여야 합니다.";

    public InvalidTitleException() {
        super(MESSAGE);
    }
}
