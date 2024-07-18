package com.application.poppool.domain.notice.controller;

import com.application.poppool.domain.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "공지사항 API")
public interface NoticeControllerDoc {

    ResponseEntity<GetNoticeListResponse> getNoticeList();

    ResponseEntity<GetNoticeDetailResponse> getNoticeDetail(@PathVariable Long id);

    void createNotice(@RequestBody @Valid CreateNoticeRequest request);

    void updateNotice(@PathVariable Long id, @RequestBody UpdateNoticeRequest request);

    void deleteNotice(@PathVariable Long id, @RequestParam String adminId);
}
