package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public class IsEqualTo extends AbstractBooleanOperator {
    public static final long ID = 3L;

    @Override
    protected boolean doEvaluate(Expression<?> left, Expression<?> right) {
        return left.evaluate().equals(right.evaluate());
    }

    @Override
    public long operatorId() {
        return ID;
    }
}
