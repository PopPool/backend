package com.application.poppool.domain.notice.controller;

import com.application.poppool.domain.notice.dto.response.GetNoticeDetailResponse;
import com.application.poppool.domain.notice.dto.response.GetNoticeListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "공지사항 조회 API")
public interface NoticeControllerDoc {

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    ResponseEntity<GetNoticeListResponse> getNoticeList();

    @Operation(summary = "공지사항 상세 조회", description = "공지사항 상세를 조회합니다.")
    ResponseEntity<GetNoticeDetailResponse> getNoticeDetail(@PathVariable Long id);

}
