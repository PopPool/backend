package com.application.poppool.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExceptionResponse {
    // 에러 코드
    int code;
    // 에러 메시지
    String message;

}
