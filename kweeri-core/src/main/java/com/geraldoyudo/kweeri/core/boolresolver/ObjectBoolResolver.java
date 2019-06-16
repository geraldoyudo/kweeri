package com.geraldoyudo.kweeri.core.boolresolver;

class ObjectBoolResolver implements BoolResolver {
    @Override
    public boolean resolve(Object object) {
        return object != null;
    }

    @Override
    public boolean canResolve(Object object) {
        return true;
    }
}
