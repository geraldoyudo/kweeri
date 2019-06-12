package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;

public abstract class AbstractBooleanOperator implements BooleanOperator {
    private Expression<?> left;
    private Expression<?> right;

    @Override
    public void setLeft(Expression<?> expression) {
        this.left = expression;
    }

    @Override
    public void setRight(Expression<?> expression) {
        this.right = expression;
    }

    @Override
    public Boolean evaluate() {
        if (left == null) {
            throw new IllegalArgumentException("No value to the left of operator");
        }
        if (right == null) {
            throw new IllegalArgumentException("No value to the right of operator");
        }
        return doEvaluate(left, right);
    }

    protected abstract boolean doEvaluate(Expression<?> left, Expression<?> right);
}
