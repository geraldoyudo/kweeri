package com.geraldoyudo.kweeri.core.mapping.valueparsers;

public class UnrecognizedValueExpression extends Exception {
    public UnrecognizedValueExpression() {
    }

    public UnrecognizedValueExpression(String message) {
        super(message);
    }
}
