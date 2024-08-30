package com.application.poppool.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

    ALREADY_EXISTS_USER_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 회원아이디입니다."),
    ALREADY_EXISTS_DATA(HttpStatus.BAD_REQUEST, "이미 존재하는 데이터입니다."),
    ALREADY_EXISTS_LIKE(HttpStatus.BAD_REQUEST, "이미 존재하는 좋아요(도움돼요)입니다."),
    ALREADY_EXISTS_BOOKMARK(HttpStatus.BAD_REQUEST, "이미 찜하신 팝업스토어입니다."),
    ALREADY_BLOCKED_USER(HttpStatus.BAD_REQUEST, "이미 차단한 유저입니다."),
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
    AUTHENTICATION_FAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    BLOCKED_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "차단한 회원이 아닙니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    POPUP_STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 팝업스토어입니다."),
    POPUP_STORE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 팝업스토어 이미지입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코멘트입니다."),
    COMMENT_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 코멘트 이미지입니다."),
    NOT_MY_COMMENT(HttpStatus.NOT_FOUND, "내가 작성한 코멘트가 아닙니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 좋아요(도움돼요)입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 찜입니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 데이터입니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 공지사항입니다."),
    DATA_MAPPING_ERROR(HttpStatus.BAD_REQUEST, "데이터가 올바르게 매핑되지 않았습니다"),
    TOKEN_BLACK_LIST_EXCEPTION(HttpStatus.UNAUTHORIZED, "해당 토큰은 블랙리스트에 등록된 토큰입니다."),
    NOT_TEMPORARY_TOKEN_ALLOWED_URL_EXCEPTION(HttpStatus.UNAUTHORIZED, "임시 토큰으로 접근할 수 없는 URL입니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token 입니다."),
    TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    LOGOUT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "로그아웃에 실패했습니다."),
    NOT_ADMIN(HttpStatus.BAD_REQUEST, "관리자 계정이 아닙니다. 관리자 계정으로 다시 시도해주세요"),
    CONCURRENCY_ERROR(HttpStatus.CONFLICT, "동시에 리소스에 접근하여 에러가 발생하였습니다.");

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private final HttpStatus status;
    private final String message;
}
