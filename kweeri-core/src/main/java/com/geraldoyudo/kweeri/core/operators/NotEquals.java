package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.expression.Expression;

public class NotEquals extends AbstractBooleanBinaryOperator implements ComparisonOperator {
    public static final long ID = 5L;

    @Override
    protected boolean doEvaluate(Object context, Expression<?> left, Expression<?> right) {
        return !left.evaluate(context).equals(right.evaluate(context));
    }

    @Override
    public long operatorId() {
        return ID;
    }
}
