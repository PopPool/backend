package com.application.poppool.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 프로젝트에서 발생한 예외를 처리할 때 사용하는 Base Exception.
 * 모든 Exception의 기반이 된다.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    protected final HttpStatus httpStatus;
    @Getter
    public String causeData;

    public BaseException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getStatus();
    }

    protected BaseException(ErrorCode errorCode, HttpStatus status) {
        super(errorCode.getMessage());
        this.httpStatus = status;
    }

    protected BaseException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }


    /**
     * ExceptionAdvise Common
     *
     * @return
     */
    public ExceptionResponse buildExceptionResponseDTO() {
        return ExceptionResponse.builder()
                .httpStatus(this.httpStatus)
                .message(getMessage())
                .build();
    }
}
