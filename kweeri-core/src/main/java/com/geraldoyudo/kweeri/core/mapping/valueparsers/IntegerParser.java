package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;

import java.util.regex.Pattern;

public class IntegerParser implements ValueParser {

    @Override
    public Expression parse(String object) {
        try {
            return new ObjectExpression(Integer.parseInt(object));
        }catch (NumberFormatException ex){
            throw new IllegalArgumentException("Invalid integer expression: " + object);
        }
    }

    @Override
    public Pattern pattern() {
        return Pattern.compile("\\d+");
    }
}
