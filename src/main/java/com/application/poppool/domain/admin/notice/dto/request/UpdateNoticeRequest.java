package com.application.poppool.domain.admin.notice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateNoticeRequest {

    @Schema(description = "공지사항 제목")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @Schema(description = "공지사항 내용")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

}
