package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.PropertyExpression;

public class PropertyPrinter implements ValuePrinter {

    @Override
    public String print(Expression expression) {
        if (!supportsExpression(expression)) {
            throw new IllegalArgumentException("Cannot support expression: " + expression);
        }
        return ((PropertyExpression) expression).getProperty();
    }

    @Override
    public boolean supportsExpression(Expression expression) {
        return expression instanceof PropertyExpression;
    }
}
