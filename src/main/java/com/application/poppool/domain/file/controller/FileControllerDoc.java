package com.application.poppool.domain.file.controller;

import com.application.poppool.domain.file.dto.request.PreSignedUrlRequest;
import com.application.poppool.domain.file.dto.response.PreSignedUrlResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "AWS S3 PreSignedUrl API")
public interface FileControllerDoc {

    ResponseEntity<PreSignedUrlResponse> getPreSignedUploadUrl(@RequestBody @Valid PreSignedUrlRequest request);

    ResponseEntity<PreSignedUrlResponse> getPreSignedDownloadUrl(@RequestBody @Valid PreSignedUrlRequest request);

}
