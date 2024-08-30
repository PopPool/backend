package com.application.poppool.domain.file.controller;


import com.application.poppool.domain.file.dto.request.PreSignedUrlRequest;
import com.application.poppool.domain.file.dto.response.PreSignedUrlResponse;
import com.application.poppool.domain.file.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController implements FileControllerDoc {

    private final FileService fileService;

    @PostMapping("/upload-preSignedUrl")
    public ResponseEntity<PreSignedUrlResponse> getPreSignedUploadUrl(@RequestBody @Valid PreSignedUrlRequest request) {
        return ResponseEntity.ok(fileService.getPreSignedUrlForUpload(request.getObjectKeyList()));
    }

    @PostMapping("/download-preSignedUrl")
    public ResponseEntity<PreSignedUrlResponse> getPreSignedDownloadUrl(@RequestBody @Valid PreSignedUrlRequest request) {
        return ResponseEntity.ok(fileService.getPreSignedUrlForDownload(request.getObjectKeyList()));
    }

}
