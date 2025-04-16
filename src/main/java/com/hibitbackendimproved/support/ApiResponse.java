package com.hibitbackendimproved.support;

import com.hibitbackendimproved.support.error.dto.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final Meta meta;
    private final T data;

    public ApiResponse(int code, String message, T data) {
        this.meta = new Meta(code, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus.value(), message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.getReasonPhrase(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return of(HttpStatus.CREATED, data);
    }

    public static <T> ApiResponse<T> noContent() {
        return of(HttpStatus.NO_CONTENT, null);
    }

    public static ApiResponse<ErrorResponse> badRequest(ErrorResponse errorResponse) {
        return of(HttpStatus.BAD_REQUEST, errorResponse.getMessage(), null);
    }

    public static ApiResponse<ErrorResponse> UnAuthorized(ErrorResponse errorResponse) {
        return of(HttpStatus.UNAUTHORIZED, errorResponse.getMessage(), null);
    }

    public static ApiResponse<ErrorResponse> NotFound(ErrorResponse errorResponse) {
        return of(HttpStatus.NOT_FOUND, errorResponse.getMessage(), null);
    }

    public static ApiResponse<ErrorResponse> internalServerError(ErrorResponse errorResponse) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, errorResponse.getMessage(), null);
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
