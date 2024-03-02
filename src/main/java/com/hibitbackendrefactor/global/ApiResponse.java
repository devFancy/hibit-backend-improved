package com.hibitbackendrefactor.global;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final Meta meta;
    private final T data;

    public ApiResponse(int code, String message, T data) {
        this.meta = new Meta(code, message);
        this.data = data;
    }

    @Getter
    private static class Meta {
        private final int code;
        private final String message;

        public Meta(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
