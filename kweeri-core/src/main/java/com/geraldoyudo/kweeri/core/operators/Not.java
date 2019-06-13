package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.expression.Expression;

public class Not extends AbstractBooleanUnaryOperator {
    private static final long ID = 4L;

    @Override
    protected boolean doEvaluate(Object context, Expression<?> expression) {
        return !expression.evaluateToBoolean(context);
    }

    @Override
    public long operatorId() {
        return ID;
    }
}
