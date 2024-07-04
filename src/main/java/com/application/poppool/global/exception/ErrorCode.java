package com.application.poppool.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

    ALREADY_EXISTS_USER_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 회원아이디입니다."),
    DATA_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "데이터가 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpStatus.BAD_REQUEST, "HTTP 요청 본문 변환 중 에러가 발생했습니다."),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "메서드 파라미터가 유효하지 않습니다."),
    NO_SUCH_ELEMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "No Such Element Exception이 발생했습니다."),
    ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "ILLEGAL_ARGUMENT_EXCEPTION이 발생했습니다."),
    NULL_POINT_EXCEPTION(HttpStatus.BAD_REQUEST, "NULL_POINT_EXCEPTION이 발생했습니다."),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(HttpStatus.BAD_REQUEST, "INDEX_OUT_OF_BOUNDS_EXCEPTION이 발생했습니다."),
    ARITHMETIC_EXCEPTION(HttpStatus.BAD_REQUEST, "ARITHMETIC_EXCEPTION이 발생했습니다."),
    MULTIPART_EXCEPTION(HttpStatus.BAD_REQUEST, "MULTIPART_EXCEPTION이 발생했씁니다."),
    DATABASE_EXCEPTION(HttpStatus.BAD_REQUEST, "DATABASE_EXCEPTION이 발생했습니다."),
    EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION이 발생했습니다."),
    AUTEHNTICATION_FAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_EXISTS_INTEREST(HttpStatus.NOT_FOUND, "존재하지 않는 관심 카테고리입니다.");

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private final HttpStatus status;
    private final String message;
}
