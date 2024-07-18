package com.application.poppool.domain.notice.controller;

import com.application.poppool.domain.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import com.application.poppool.domain.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController implements NoticeControllerDoc{

    private final NoticeService noticeService;

    @Override
    @GetMapping("/list")
    public ResponseEntity<GetNoticeListResponse> getNoticeList() {
        return ResponseEntity.ok(noticeService.getNoticeList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeDetailResponse> getNoticeDetail(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNoticeDetail(id));
    }

    @Override
    @PostMapping("")
    public void createNotice(@RequestBody @Valid CreateNoticeRequest request) {
        noticeService.createNotice(request);
    }

    @Override
    @PutMapping("/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody @Valid UpdateNoticeRequest request) {
        noticeService.updateNotice(id, request);
    }

    @Override
    @DeleteExchange("/{id}")
    public void deleteNotice(@PathVariable Long id, @RequestBody @Valid String adminId) {
        noticeService.deleteNotice(id, adminId);
    }


}
