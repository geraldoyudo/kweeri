package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;

public class StringPrinter implements ValuePrinter {
    private boolean useDoubleQuotes;

    public StringPrinter(boolean useDoubleQuotes) {
        this.useDoubleQuotes = useDoubleQuotes;
    }

    public StringPrinter() {

    }

    @Override
    public String print(Expression expression) {
        if (!supportsExpression(expression)) {
            throw new IllegalArgumentException("cannot support expression: " + expression);
        }
        String format = useDoubleQuotes ? "\"%s\"" : "'%s'";
        return String.format(format, (String) ((ObjectExpression) expression).getObject());
    }

    @Override
    public boolean supportsExpression(Expression expression) {
        if (!(expression instanceof ObjectExpression)) {
            return false;
        }
        return (((ObjectExpression) expression).getObject() instanceof String);
    }
}
