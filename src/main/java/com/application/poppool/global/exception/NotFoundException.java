package com.application.poppool.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode, HTTP_STATUS);
    }

}
