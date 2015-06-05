package com.github.gchudnov.squel;

/**
 * Exception
 */
public class SquelException extends Exception {

    public SquelException() {
    }

    public SquelException(String detailMessage) {
        super(detailMessage);
    }

    public SquelException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SquelException(Throwable throwable) {
        super(throwable);
    }

}
