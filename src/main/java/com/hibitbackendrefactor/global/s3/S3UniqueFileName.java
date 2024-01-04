package com.hibitbackendrefactor.global.s3;

import com.hibitbackendrefactor.post.exception.NotFoundImageFileException;

import java.util.UUID;

public class S3UniqueFileName {
    public static String generateUniqueFileName(String originalFileName) {
        // 이미지 파일 이름 중복 방지를 위해 고유한 파일명 생성(확장자(extension)는 유지)
        if(originalFileName == null) {
            throw new NotFoundImageFileException();
        }
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        return UUID.randomUUID() + extension;
    }
}
