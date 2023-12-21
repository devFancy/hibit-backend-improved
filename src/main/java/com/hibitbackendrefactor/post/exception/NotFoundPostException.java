package com.hibitbackendrefactor.post.exception;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException(final String message) {
        super(message);
    }

    public NotFoundPostException() {
        this("게시글을 찾을 수 없습니다.");
    }
}
