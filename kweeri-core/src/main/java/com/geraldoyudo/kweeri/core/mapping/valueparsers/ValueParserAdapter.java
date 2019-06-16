package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class ValueParserAdapter {

    private List<ValueParser> parsers = new LinkedList<>();

    public ValueParserAdapter addParsers(ValueParser... parser) {
        this.parsers.addAll(asList(parser));
        return this;
    }

    public Expression parse(String valueExpression) throws UnrecognizedValueExpression {
        for (ValueParser parser : parsers) {
            if (parser.pattern().matcher(valueExpression).matches()) {
                return parser.parse(valueExpression);
            }
        }
        throw new UnrecognizedValueExpression("Could not recognize value pattern");
    }

    public Pattern getCombinedPattern() {
        StringBuilder builder = new StringBuilder();
        for (ValueParser parser : parsers) {
            builder.append(parser.pattern().pattern());
            builder.append("|");
        }
        return Pattern.compile(builder.toString().substring(0, builder.length() - 1));
    }
}
