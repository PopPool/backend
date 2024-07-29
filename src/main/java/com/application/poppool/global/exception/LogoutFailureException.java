package com.application.poppool.global.exception;

public class LogoutFailureException extends RuntimeException{
    public LogoutFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
