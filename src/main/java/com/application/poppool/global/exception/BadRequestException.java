package com.application.poppool.global.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    private static final int ERR_CODE = 4000;
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BadRequestException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }

    public BadRequestException(ErrorCode errorCodes) {
        super(errorCodes, HTTP_STATUS);
    }

    public BadRequestException(int errCode, String defaultMessage) {
        super(errCode, HTTP_STATUS, defaultMessage);
    }

    public ExceptionResponse buildBadRequestExceptionResponseDTO() {
        return ExceptionResponse.builder()
                .code(this.code)
                .message(getMessage())
                .build();
    }
}