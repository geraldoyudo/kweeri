package com.geraldoyudo.kweeri.core.boolresolver;

class BooleanBoolResolver implements BoolResolver {
    @Override
    public boolean resolve(Object object) {
        return (Boolean) object;
    }

    @Override
    public boolean canResolve(Object object) {
        return object instanceof Boolean;
    }
}
