package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;

public class ObjectPrinter implements ValuePrinter {

    @Override
    public String print(Expression expression) {
        if (!supportsExpression(expression)) {
            throw new IllegalArgumentException("cannot support expression: " + expression);
        }
        return ((ObjectExpression) expression).getObject().toString();
    }

    @Override
    public boolean supportsExpression(Expression expression) {
        if (!(expression instanceof ObjectExpression)) {
            return false;
        }
        return !(((ObjectExpression) expression).getObject() instanceof String);
    }
}
