package com.geraldoyudo.kweeri.core.converters;

public class QueryNotSupportedException extends RuntimeException {
    public QueryNotSupportedException() {
    }

    public QueryNotSupportedException(String s) {
        super(s);
    }
}
