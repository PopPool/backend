package com.application.poppool.domain.notice.controller;

import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import com.application.poppool.domain.notice.service.NoticeService;
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
@RequestMapping("/notice")
public class NoticeController implements NoticeControllerDoc {

    private final NoticeService noticeService;

    @Override
    @GetMapping("/list")
    public ResponseEntity<GetNoticeListResponse> getNoticeList() {
        log.info("Get NoticeList");
        return ResponseEntity.ok(noticeService.getNoticeList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GetNoticeDetailResponse> getNoticeDetail(@PathVariable Long id) {
        log.info("Get Notice Detail");
        return ResponseEntity.ok(noticeService.getNoticeDetail(id));
    }

}
