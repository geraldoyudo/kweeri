package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public class Or extends AbstractBooleanOperator {
    public static final long ID = 1L;

    @Override
    protected boolean doEvaluate(Expression<?> left, Expression<?> right) {

        return left.evaluateToBoolean() || right.evaluateToBoolean();
    }

    @Override
    public long operatorId() {
        return ID;
    }
}
