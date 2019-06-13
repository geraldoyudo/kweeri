package com.geraldoyudo.kweeri.core.converters;

import com.geraldoyudo.kweeri.core.expression.Expression;

public interface QueryConverter <T> {

    T convertExpressionToQuery(Expression expression);
}
