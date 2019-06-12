package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.Expression;

public interface QuerySerializer {

    Expression serialize(String queryString);
}
