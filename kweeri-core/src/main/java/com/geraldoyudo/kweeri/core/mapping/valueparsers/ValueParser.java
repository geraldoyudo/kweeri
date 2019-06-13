package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;

import java.util.regex.Pattern;

public interface ValueParser {

    Expression parse(String object);

    Pattern pattern();
}
