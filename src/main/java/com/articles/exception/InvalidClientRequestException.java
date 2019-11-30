package com.articles.exception;

import org.springframework.http.HttpStatus;

public class InvalidClientRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

    public InvalidClientRequestException(String message) {
        super(message);
    }

    public InvalidClientRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
