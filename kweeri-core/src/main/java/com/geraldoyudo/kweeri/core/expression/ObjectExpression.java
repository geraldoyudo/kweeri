package com.geraldoyudo.kweeri.core.expression;

import java.util.Objects;

public class ObjectExpression implements Expression<Object> {
    private Object object;

    public ObjectExpression(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public Object evaluate(Object context) {
        return object;
    }

    @Override
    public boolean equals(Object object1) {
        if (this == object1) return true;
        if (object1 == null || getClass() != object1.getClass()) return false;
        ObjectExpression that = (ObjectExpression) object1;
        return Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object);
    }
}
