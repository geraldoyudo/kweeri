package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public class Or extends AbstractBooleanOperator {

    @Override
    protected boolean doEvaluate(Expression<?> left, Expression<?> right) {

        return left.evaluateToBoolean() || right.evaluateToBoolean();
    }
}
