package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;

public interface ValuePrinter {

    String print(Expression expression);

    boolean supportsExpression(Expression expression);
}
