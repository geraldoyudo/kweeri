package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public interface Operator<T> extends Expression<T> {

    void setLeft(Expression<?> expression);

    void setRight(Expression<?> expression);
}
