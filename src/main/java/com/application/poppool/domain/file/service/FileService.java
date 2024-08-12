package com.application.poppool.domain.file.service;

import com.application.poppool.domain.file.dto.PreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    public PreSignedUrlResponse generatePreSignedUrlForUpload(String objectKey) {
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(r -> r.bucket(bucketName).key(objectKey))
                .build();

        String preSignedUrl = s3Presigner.presignPutObject(presignRequest).url().toString();

        return PreSignedUrlResponse.builder()
                .preSignedUrl(preSignedUrl)
                .build();

    }

    public PreSignedUrlResponse generatePreSignedUrlForDownload(String objectKey) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(r -> r.bucket(bucketName).key(objectKey))
                .build();

        String preSignedUrl = s3Presigner.presignGetObject(presignRequest).url().toString();

        return PreSignedUrlResponse.builder()
                .preSignedUrl(preSignedUrl)
                .build();

    }

}
