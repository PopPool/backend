package com.application.poppool.domain.admin.notice.controller;

import com.application.poppool.domain.admin.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.admin.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.domain.admin.notice.service.AdminNoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminNoticeController implements AdminNoticeControllerDoc {

    private final AdminNoticeService adminNoticeService;

    @Override
    @PostMapping("")
    public void createNotice(@RequestBody @Valid CreateNoticeRequest request) {
        log.info("Create Notice");
        adminNoticeService.createNotice(request);
    }

    @Override
    @PutMapping("/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody @Valid UpdateNoticeRequest request) {
        log.info("Update Notice");
        adminNoticeService.updateNotice(id, request);
    }

    @Override
    @DeleteExchange("/{id}")
    public void deleteNotice(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Delete Notice");
        adminNoticeService.deleteNotice(id, userDetails.getUsername());
    }

}
