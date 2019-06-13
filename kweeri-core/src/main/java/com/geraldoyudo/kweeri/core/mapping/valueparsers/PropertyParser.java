package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.PropertyExpression;

import java.util.regex.Pattern;

public class PropertyParser implements ValueParser {
    private static Pattern pattern = Pattern.compile("[\\w_\\d]+(\\.[\\w_\\d]+)*");

    @Override
    public Expression parse(String object) {
        if (object == null || !pattern.matcher(object).matches()) {
            throw new IllegalArgumentException("Invalid property: " + object);
        }
        return new PropertyExpression(object);
    }

    @Override
    public Pattern pattern() {
        return pattern;
    }
}
