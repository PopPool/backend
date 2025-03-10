package com.application.poppool.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleException(BaseException e, WebRequest request) {  //상속받는 모든 Exception 공통 핸들러
        return handleExceptionInternal(e, e.buildExceptionResponseDTO(), new HttpHeaders(), e.getHttpStatus(), request);
    }

    private ErrorCode getHttpMessageNotReadableErrorCode(HttpMessageNotReadableException ex) {
        return ex.getCause() instanceof InvalidFormatException
                ? ErrorCode.DATA_VALIDATION_ERROR
                : ErrorCode.HTTP_MESSAGE_NOT_READABLE_EXCEPTION;
    }

    /**
     * DTO에 대한 Validation Check Error를 핸들링하는 전역 Exeption Handler
     *
     * @return BadRequestException
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatusCode status, WebRequest request) {
        BadRequestException badRequestFromViolation;
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            fieldErrorList.forEach(fe -> log.error("Validation Error - FieldName : {}, MSG : {}", fe.getField(), fe.getDefaultMessage()));

            if (!fieldErrorList.isEmpty()) {
                FieldError fieldError = fieldErrorList.get(0);
                String msg = fieldError.getDefaultMessage();
                if (!msg.isBlank()) {
                    badRequestFromViolation = new BadRequestException(ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getStatus(), fieldError.getDefaultMessage());
                    return handleException(badRequestFromViolation, request);
                }
            }
        }
        badRequestFromViolation = new BadRequestException(ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
        return handleException(badRequestFromViolation, request);
    }


     @ExceptionHandler(NotFoundException.class)
     public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        log.error("NotFoundException : {}", ex.getMessage(), ex);
        return handleException(ex, request);
     }

    /**
     * BaseException을 비롯해서 BaseException을 상속받는 모든 커스텀 예외를 이 핸들러가 처리
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException e, WebRequest request) {
        log.error("handleBaseException: {}", e.getMessage(), e);
        return handleExceptionInternal(e, e.buildExceptionResponseDTO(), new HttpHeaders(), e.getHttpStatus(), request);
    }

    /**
     * BaseException 보다 상위인 런타임 예외를 이 핸들러가 처리
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("handleRunTimeException(unchecked exception) : {}", ex.getMessage(), ex);

        Object body = ExceptionResponse.builder()
                .message(ErrorCode.RUNTIME_EXCEPTION.getMessage())
                .build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * 런타임 예외가 아닌 체크 예외를 이 핸들러가 처리
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCheckedException(Exception ex, WebRequest request) {
        log.error("handleException(checked exception) : {}", ex.getMessage(), ex);

        Object body = ExceptionResponse.builder()
                .message(ErrorCode.EXCEPTION.getMessage())
                .build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
