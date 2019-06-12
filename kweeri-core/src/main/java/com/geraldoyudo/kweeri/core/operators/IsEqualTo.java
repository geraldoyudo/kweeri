package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public class IsEqualTo extends AbstractBooleanOperator {
    @Override
    protected boolean doEvaluate(Expression<?> left, Expression<?> right) {
        return left.evaluate().equals(right.evaluate());
    }
}
