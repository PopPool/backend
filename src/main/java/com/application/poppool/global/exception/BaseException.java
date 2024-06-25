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
    protected final int code;
    @Getter
    protected final HttpStatus httpStatus;
    @Getter
    public String causeData;

    public BaseException(String message) {
        super(message);
        this.code = 5000;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = 5000;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    protected BaseException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.httpStatus = httpStatus;
    }

    protected BaseException(int code, HttpStatus httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }


    /**
     * ExceptionAdvise Common
     *
     * @return
     */
    public ExceptionResponse buildExceptionResponseDTO() {
        return ExceptionResponse.builder()
                .code(this.code)
                .message(getMessage())
                .build();
    }

}
