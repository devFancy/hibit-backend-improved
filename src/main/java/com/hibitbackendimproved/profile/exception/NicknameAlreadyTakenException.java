package com.hibitbackendimproved.profile.exception;


public class NicknameAlreadyTakenException extends RuntimeException {
    public NicknameAlreadyTakenException(final String message) {
        super(message);
    }

    public NicknameAlreadyTakenException() {
        this("이미 사용중인 닉네임 입니다.");
    }
}
