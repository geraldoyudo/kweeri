package com.geraldoyudo.kweeri.core;

import com.geraldoyudo.kweeri.core.boolresolver.BooleanResolver;

public interface Expression<T> {

    T evaluate();

    default boolean evaluateToBoolean(){
        return BooleanResolver.instance().resolve(evaluate());
    }
}
