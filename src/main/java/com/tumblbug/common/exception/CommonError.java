package com.tumblbug.common.exception;

import org.springframework.http.HttpStatus;

public enum CommonError {

    COMMON_BAD_REQUEST("0000001", HttpStatus.BAD_REQUEST),
    COMMON_INTERNAL_SERVER_ERROR("0000002", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMON_NOT_FOUND("0000003", HttpStatus.NOT_FOUND);

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
