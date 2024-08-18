package com.application.poppool.domain.file.service;

import com.application.poppool.domain.file.dto.response.PreSignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;


    public PreSignedUrlResponse getPreSignedUrlForUpload(List<String> objectKeys) {
        List<PreSignedUrlResponse.PreSignedUrl> preSignedUrlList = objectKeys.stream()
                .map(this::generatePreSignedUrlForUpload)
                .toList();

        return PreSignedUrlResponse.builder()
                .preSignedUrlList(preSignedUrlList)
                .build();
    }

    public PreSignedUrlResponse getPreSignedUrlForDownload(List<String> objectKeys) {
        List<PreSignedUrlResponse.PreSignedUrl> preSignedUrlList = objectKeys.stream()
                .map(this::generatePreSignedUrlForDownload)
                .toList();

        return PreSignedUrlResponse.builder()
                .preSignedUrlList(preSignedUrlList)
                .build();
    }


    private PreSignedUrlResponse.PreSignedUrl generatePreSignedUrlForUpload(String objectKey) {
        PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(r -> r.bucket(bucketName).key(objectKey))
                .build();

        String preSignedUrl = s3Presigner.presignPutObject(preSignRequest).url().toString();

        return PreSignedUrlResponse.PreSignedUrl.builder()
                .objectKey(objectKey)
                .preSignedUrl(preSignedUrl)
                .build();
    }

    private PreSignedUrlResponse.PreSignedUrl generatePreSignedUrlForDownload(String objectKey) {
        GetObjectPresignRequest preSignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(r -> r.bucket(bucketName).key(objectKey))
                .build();

        String preSignedUrl = s3Presigner.presignGetObject(preSignRequest).url().toString();

        return PreSignedUrlResponse.PreSignedUrl.builder()
                .objectKey(objectKey)
                .preSignedUrl(preSignedUrl)
                .build();

    }
}