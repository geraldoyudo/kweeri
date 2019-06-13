package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;

public interface QueryDeserializer {

    String deserialize(Expression expression);
}
