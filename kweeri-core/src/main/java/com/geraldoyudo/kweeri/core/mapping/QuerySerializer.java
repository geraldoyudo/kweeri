package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;

public interface QuerySerializer {

    Expression deSerialize(String queryString);
}
