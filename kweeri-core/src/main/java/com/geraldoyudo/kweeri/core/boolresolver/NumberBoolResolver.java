package com.geraldoyudo.kweeri.core.boolresolver;

class NumberBoolResolver implements BoolResolver {
    @Override
    public boolean resolve(Object number) {
        return ((Number) number).doubleValue() > 0;
    }

    @Override
    public boolean canResolve(Object object) {
        return object instanceof Number;
    }
}
