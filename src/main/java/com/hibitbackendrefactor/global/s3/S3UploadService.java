package com.hibitbackendrefactor.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3UploadService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String saveFile(final MultipartFile multipartFile, final String uniqueFileName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, uniqueFileName, multipartFile.getInputStream(), metadata);
        // 저장된 파일의 s3 url 반환
        return amazonS3Client.getUrl(bucket, uniqueFileName).toString();
    }

    public void deleteFile(final String imageUrl) throws MalformedURLException {
        URL s3Url = new URL(imageUrl);
        String bucket = s3Url.getHost().split("\\.")[0];
        String objectFileName = s3Url.getPath().substring(1);

        amazonS3Client.deleteObject(bucket, objectFileName);
    }

}