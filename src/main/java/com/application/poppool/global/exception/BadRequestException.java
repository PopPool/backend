package com.application.poppool.global.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

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

    public BadRequestException(HttpStatus status, String defaultMessage) {
        super(HTTP_STATUS, defaultMessage);
    }

    public ExceptionResponse buildBadRequestExceptionResponseDTO() {
        return ExceptionResponse.builder()
                .httpStatus(this.httpStatus)
                .message(getMessage())
                .build();
    }
}