package com.application.poppool.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExceptionResponse {
    // 상태 코드
    HttpStatus httpStatus;
    // 메시지
    String message;

}
