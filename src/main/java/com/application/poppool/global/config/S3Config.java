package com.application.poppool.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;


    private static final ProfileCredentialsProvider CREDENTIALS_PROVIDER = ProfileCredentialsProvider.create();

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region)) // 원하는 리전으로 변경
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(S3Client s3Client) {
        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(CREDENTIALS_PROVIDER)
                .build();
    }
}
