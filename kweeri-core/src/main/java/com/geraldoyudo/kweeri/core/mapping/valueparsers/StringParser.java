package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser implements ValueParser {

    public static final Pattern PATTERN = Pattern.compile("[']([^']+)[']|[\"]([^\"]+)[\"]");

    @Override
    public Expression parse(String object) {
        Matcher matcher = PATTERN.matcher(object);
        if(!matcher.find()){
            throw new IllegalArgumentException("Invalid string expression value: " + object);
        }
        String string = matcher.group(1) != null ? matcher.group(1): matcher.group(2);
        return new ObjectExpression(string);
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
