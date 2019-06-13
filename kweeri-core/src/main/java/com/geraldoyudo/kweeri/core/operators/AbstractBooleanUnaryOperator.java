package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.expression.Expression;

import java.util.Objects;

public abstract class AbstractBooleanUnaryOperator implements BooleanOperator, UnaryOperator {
    private Expression<?> left;

    @Override
    public void setLeft(Expression<?> expression) {
        this.left = expression;
    }

    @Override
    public void setRight(Expression<?> expression) {
        throw new IllegalArgumentException("Not supported");
    }

    @Override
    public Boolean evaluate(Object context) {
        if (left == null) {
            throw new IllegalArgumentException("No value to the left of operator");
        }
        return doEvaluate(context, left);
    }

    @Override
    public Expression<?> getLeft() {
        return left;
    }

    @Override
    public Expression<?> getRight() {
        throw new IllegalArgumentException("Right operator not supported");
    }

    protected abstract boolean doEvaluate(Object context, Expression<?> expression);

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractBooleanUnaryOperator that = (AbstractBooleanUnaryOperator) object;
        return Objects.equals(left, that.left);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operatorId(), left);
    }
}
