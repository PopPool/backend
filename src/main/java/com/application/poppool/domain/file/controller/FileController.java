package com.application.poppool.domain.file.controller;


import com.application.poppool.domain.file.dto.PreSignedUrlResponse;
import com.application.poppool.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{objectKey}/upload-url")
    public ResponseEntity<PreSignedUrlResponse> getPreSignedUploadUrl(@PathVariable String objectKey) {
        return ResponseEntity.ok(fileService.generatePreSignedUrlForUpload(objectKey));
    }

    @GetMapping("/{objectKey}/download-url")
    public ResponseEntity<PreSignedUrlResponse> getPreSignedDownloadUrl(@PathVariable String objectKey) {
        return ResponseEntity.ok(fileService.generatePreSignedUrlForDownload(objectKey));
    }

}
