package com.geraldoyudo.kweeri.core.mapping;

public class QueryProcessingException extends RuntimeException {
    public QueryProcessingException() {
    }

    public QueryProcessingException(String message) {
        super(message);
    }

    public QueryProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryProcessingException(Throwable cause) {
        super(cause);
    }
}
