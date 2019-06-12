package com.geraldoyudo.kweeri.core;

public class ObjectExpression implements Expression<Object> {
    private Object object;

    public ObjectExpression(Object object) {
        this.object = object;
    }

    @Override
    public Object evaluate() {
        return object;
    }
}
