package com.application.poppool.domain.notice.controller;

import com.application.poppool.domain.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.notice.dto.request.UpdateNoticeRequest;
import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "공지사항 API")
public interface NoticeControllerDoc {

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    ResponseEntity<GetNoticeListResponse> getNoticeList();

    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세를 조회합니다.")
    ResponseEntity<GetNoticeDetailResponse> getNoticeDetail(@PathVariable Long id);

    @Operation(summary = "공지사항 작성", description = "공지사항을 작성합니다.")
    void createNotice(@RequestBody @Valid CreateNoticeRequest request);

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다,")
    void updateNotice(@PathVariable Long id, @RequestBody UpdateNoticeRequest request);

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    void deleteNotice(@PathVariable Long id, @RequestParam String adminId);
}
