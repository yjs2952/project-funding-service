package com.tumblbug.common.exception;

import org.springframework.http.HttpStatus;

public enum CommonError {

    COMMON_INTERNAL_SERVER_ERROR("0000001", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMON_BAD_REQUEST("0000002", HttpStatus.BAD_REQUEST),
    COMMON_UNAUTHORIZED("COM1N0003", HttpStatus.UNAUTHORIZED),
    COMMON_NOT_FOUND("COM1N0004", HttpStatus.NOT_FOUND);

    private HttpStatus httpStatus;
    private String code;

    CommonError(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public int getStatus() {
        return this.httpStatus.value();
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
