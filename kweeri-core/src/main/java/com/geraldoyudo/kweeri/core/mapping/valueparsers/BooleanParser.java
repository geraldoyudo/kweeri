package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;

import java.util.regex.Pattern;

public class BooleanParser implements ValueParser {
    private static final Pattern PATTERN = Pattern.compile("true|false");

    @Override
    public Expression parse(String object) {
        if (object == null || !PATTERN.matcher(object).matches()) {
            throw new IllegalArgumentException("Cannot parse value: " + object);
        }
        return new ObjectExpression(Boolean.parseBoolean(object));
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
