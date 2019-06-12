package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public interface Operator<T> extends Expression<T> {
    long operatorId();

    void setLeft(Expression<?> expression);

    void setRight(Expression<?> expression);
}
