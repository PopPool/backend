package com.application.poppool.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
            fieldErrorList.forEach(fe -> log.debug("Validation Error - FieldName : {}, MSG : {}", fe.getField(), fe.getDefaultMessage()));

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

    /**
     //공통예외처리 적용 개발시엔 주석처리후 디버깅 가능
     @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<Object> handleNoSuchElementException(
     NoSuchElementException ex, WebRequest request) {
     BadRequestException badRequestFromViolation;
     log.debug("handleNoSuchElementException : {}", ex.getMessage(), ex);

     badRequestFromViolation = new BadRequestException(ErrorCode.NO_SUCH_ELEMENT_EXCEPTION);
     return handleException(badRequestFromViolation, request);
     }

     @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

     log.debug("handleIllegalArgumentException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex, responseEntity.getBody(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
     }

     @ExceptionHandler(NullPointerException.class) public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {

     log.debug("handleNullPointerException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.NULL_POINT_EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex, responseEntity.getBody(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
     }

     @ExceptionHandler(IndexOutOfBoundsException.class) public ResponseEntity<Object> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex, WebRequest request) {

     log.debug("handleIndexOutOfBoundsException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.INDEX_OUT_OF_BOUNDS_EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex, responseEntity.getBody(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
     }

     @ExceptionHandler(ArithmeticException.class) public ResponseEntity<Object> handleArithmeticException(ArithmeticException ex, WebRequest request) {

     log.debug("handleArithmeticException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.ARITHMETIC_EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex,responseEntity.getBody() , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
     }

     @ExceptionHandler(MultipartException.class) public ResponseEntity<Object> handleMultipartException(MultipartException ex, WebRequest request) {

     log.debug("handleMultipartException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.MULTIPART_EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex,responseEntity.getBody() , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
     }

     @ExceptionHandler({PersistenceException.class, JpaSystemException.class, RollbackException.class, DataIntegrityViolationException.class,
     NonUniqueResultException.class, ConstraintViolationException.class, QueryException.class, TransactionException.class, DuplicateKeyException.class})
     public ResponseEntity<Object> handleDataBaseException(Exception ex,  WebRequest request) {
     log.debug("handleDatabaseException : {}", ex.getMessage(), ex);
     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.DATABASE_EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex,responseEntity.getBody() , new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

     }

     @ExceptionHandler(RuntimeException.class) public ResponseEntity<Object> handleRunTimeException(RuntimeException ex,  WebRequest request) {
     log.debug("handleRuntimeException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex,responseEntity.getBody() , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

     }

     @ExceptionHandler(Exception.class) public ResponseEntity<Object> handleExceptionClass(Exception ex,  WebRequest request) {
     log.debug("handleException : {}", ex.getMessage(), ex);

     ResponseEntity<Object> responseEntity = ResponseEntity.badRequest()
     .body(new BadRequestException(ErrorCode.EXCEPTION).buildExceptionResponseDTO());
     return handleExceptionInternal(ex,responseEntity.getBody() , new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
     }
     */
}
