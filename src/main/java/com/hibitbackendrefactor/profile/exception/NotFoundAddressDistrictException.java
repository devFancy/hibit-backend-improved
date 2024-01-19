package com.hibitbackendrefactor.profile.exception;

public class NotFoundAddressDistrictException extends RuntimeException {
    public NotFoundAddressDistrictException(final String message) {
        super(message);
    }

    public NotFoundAddressDistrictException() {
        this("존재하지 구입니다.");
    }
}
