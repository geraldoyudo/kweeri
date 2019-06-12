package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;

public class BasicQuerySerializer implements QuerySerializer {
    @Override
    public Expression serialize(String queryString) {
        return new ObjectExpression(false);
    }
}
