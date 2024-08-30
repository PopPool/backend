package com.application.poppool.global.exception;

import org.springframework.http.HttpStatus;

public class ConcurrencyException extends BaseException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    public ConcurrencyException(ErrorCode errorCode) {
        super(errorCode, HTTP_STATUS);
    }


}
