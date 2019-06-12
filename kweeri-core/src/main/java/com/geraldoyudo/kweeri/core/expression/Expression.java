package com.geraldoyudo.kweeri.core.expression;

import com.geraldoyudo.kweeri.core.boolresolver.BooleanResolver;

import java.io.Serializable;
import java.util.HashMap;

public interface Expression<T> extends Serializable {
    Object NONE = new HashMap<>();

    T evaluate(Object context);

    default T evaluate(){
        return evaluate(NONE);
    }

    default boolean evaluateToBoolean(){
        return BooleanResolver.instance().resolve(evaluate());
    }

    default boolean evaluateToBoolean(Object context) {
        return BooleanResolver.instance().resolve(evaluate(context));
    }
}
