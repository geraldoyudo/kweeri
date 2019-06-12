package com.geraldoyudo.kweeri.core.boolresolver;

class StringBoolResolver implements BoolResolver {

    @Override
    public boolean resolve(Object string) {
        if (string == null) {
            return false;
        }
        switch ((String) string) {
            case "":
            case "false":
                return false;
            case "true":
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean canResolve(Object object) {
        return object instanceof String;
    }
}
