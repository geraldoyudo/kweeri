package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.expression.Expression;

public class Or extends AbstractBooleanBinaryOperator {
    public static final long ID = 1L;

    @Override
    protected boolean doEvaluate(Object context, Expression<?> left, Expression<?> right) {

        return left.evaluateToBoolean(context) || right.evaluateToBoolean(context);
    }

    @Override
    public long operatorId() {
        return ID;
    }
}
