package com.geraldoyudo.kweeri.core.expression;

import com.geraldoyudo.kweeri.core.operators.*;

public class ExpressionBuilder {
    private Expression root;

    private ExpressionBuilder(Expression root) {
        this.root = root;
    }

    public ExpressionBuilder and(Expression expression) {
        return addExpressionToOperator(expression, new And());
    }

    public ExpressionBuilder and(ExpressionBuilder expressionBuilder) {
        return addExpressionToOperator(expressionBuilder.build(), new And());
    }

    public ExpressionBuilder or(Expression expression) {
        return addExpressionToOperator(expression, new Or());
    }

    public ExpressionBuilder or(ExpressionBuilder expressionBuilder) {
        return addExpressionToOperator(expressionBuilder.build(), new Or());
    }

    public ExpressionBuilder negate(){
        Not not = new Not();
        not.setLeft(root);
        this.root = not;
        return this;
    }

    public ExpressionBuilder equalTo(Expression expression) {
        return addExpressionToOperator(expression, new Equals());
    }

    public ExpressionBuilder equalTo(ExpressionBuilder expressionBuilder) {
        return addExpressionToOperator(expressionBuilder.build(), new Equals());
    }

    private ExpressionBuilder addExpressionToOperator(Expression expression, Operator operator) {
        operator.setLeft(root);
        operator.setRight(expression);
        this.root = operator;
        return this;
    }

    public static ExpressionBuilder value(Object object) {
        return new ExpressionBuilder(new ObjectExpression(object));
    }

    public static ExpressionBuilder expression(Expression expression) {
        return new ExpressionBuilder(expression);
    }

    public static ExpressionBuilder expression(ExpressionBuilder expressionBuilder) {
        return new ExpressionBuilder(expressionBuilder.build());
    }

    public static ExpressionBuilder property(String property) {
        return new ExpressionBuilder(new PropertyExpression(property));
    }

    public Expression build() {
        return root;
    }
}
