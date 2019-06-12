package com.geraldoyudo.kweeri.core;

import com.geraldoyudo.kweeri.core.boolresolver.BooleanResolver;

import java.io.Serializable;

public interface Expression<T> extends Serializable {

    T evaluate();

    default boolean evaluateToBoolean(){
        return BooleanResolver.instance().resolve(evaluate());
    }
}
