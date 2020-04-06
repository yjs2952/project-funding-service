package com.tumblbug.domain.project;

public class ExceedMaxAmountException extends RuntimeException {

    public ExceedMaxAmountException() {
        super();
    }

    public ExceedMaxAmountException(String message) {
        super(message);
    }

    public ExceedMaxAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceedMaxAmountException(Throwable cause) {
        super(cause);
    }

    protected ExceedMaxAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
