package com.geraldoyudo.kweeri.core.boolresolver;

public interface BoolResolver {

    boolean resolve(Object object);

    boolean canResolve(Object object);
}
