package com.tumblbug.domain.project;

public class ExceedMaxCountException extends RuntimeException {

    public ExceedMaxCountException() {
    }

    public ExceedMaxCountException(String message) {
        super(message);
    }

    public ExceedMaxCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceedMaxCountException(Throwable cause) {
        super(cause);
    }

    public ExceedMaxCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
