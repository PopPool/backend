package com.application.poppool.domain.file.controller;

import com.application.poppool.domain.file.dto.request.PreSignedUrlRequest;
import com.application.poppool.domain.file.dto.response.PreSignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "AWS S3 PreSignedUrl API")
public interface FileControllerDoc {

    @Operation(summary = "AWS S3 PreSignedUrl(업로드) 조회", description = "AWS S3 PreSignedUrl(업로드)을 조회합니다.")
    ResponseEntity<PreSignedUrlResponse> getPreSignedUploadUrl(@RequestBody @Valid PreSignedUrlRequest request);

    @Operation(summary = "AWS S3 PreSignedUrl(다운로드) 조회", description = "AWS S3 PreSignedUrl(다운로드)을 조회합니다.")
    ResponseEntity<PreSignedUrlResponse> getPreSignedDownloadUrl(@RequestBody @Valid PreSignedUrlRequest request);

}
