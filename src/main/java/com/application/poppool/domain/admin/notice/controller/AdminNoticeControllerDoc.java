package com.application.poppool.domain.admin.notice.controller;

import com.application.poppool.domain.admin.notice.dto.request.CreateNoticeRequest;
import com.application.poppool.domain.admin.notice.dto.request.UpdateNoticeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "관리자 페이지 공지사항 작성,수정,삭제 API")
public interface AdminNoticeControllerDoc {

    @Operation(summary = "공지사항 작성", description = "공지사항을 작성합니다.")
    void createNotice(@RequestBody @Valid CreateNoticeRequest request);

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다,")
    void updateNotice(@PathVariable Long id, @RequestBody UpdateNoticeRequest request);

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    void deleteNotice(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails);

}
