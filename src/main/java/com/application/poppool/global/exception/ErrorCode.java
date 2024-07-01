package com.application.poppool.global.exception;

import lombok.Getter;


@Getter
public enum ErrorCode {

    ALREADY_EXISTS_USER_ID(4001, "이미 존재하는 회원아이디입니다."),
    DATA_VALIDATION_ERROR(4002, "데이터가 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(4003, "HTTP 요청 본문 변환 중 에러가 발생했습니다."),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(4004, "메서드 파라미터가 유효하지 않습니다."),
    NO_SUCH_ELEMENT_EXCEPTION(4005, "No Such Element Exception이 발생했습니다."),
    ILLEGAL_ARGUMENT_EXCEPTION(4006, "ILLEGAL_ARGUMENT_EXCEPTION이 발생했습니다."),
    NULL_POINT_EXCEPTION(4007, "NULL_POINT_EXCEPTION이 발생했습니다."),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(4008, "INDEX_OUT_OF_BOUNDS_EXCEPTION이 발생했습니다."),
    ARITHMETIC_EXCEPTION(4009, "ARITHMETIC_EXCEPTION이 발생했습니다."),
    MULTIPART_EXCEPTION(4010, "MULTIPART_EXCEPTION이 발생했씁니다."),
    DATABASE_EXCEPTION(4011, "DATABASE_EXCEPTION이 발생했습니다."),
    EXCEPTION(4012, "EXCEPTION이 발생했습니다."),
    AUTEHNTICATION_FAIL_EXCEPTION(4013, "인증에 실패했습니다."),
    NOT_EXISTS_USER(4014, "존재하지 않는 회원입니다.");
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;
    private final String message;
}
