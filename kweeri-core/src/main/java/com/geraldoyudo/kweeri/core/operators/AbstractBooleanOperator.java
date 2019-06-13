package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.expression.Expression;

import java.util.Objects;

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
    public Boolean evaluate(Object context) {
        if (left == null) {
            throw new IllegalArgumentException("No value to the left of operator");
        }
        if (right == null) {
            throw new IllegalArgumentException("No value to the right of operator");
        }
        return doEvaluate(context, left, right);
    }

    protected abstract boolean doEvaluate(Object context, Expression<?> left, Expression<?> right);

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractBooleanOperator that = (AbstractBooleanOperator) object;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operatorId(), left, right);
    }
}
