package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.PropertyExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PropertyParserTest {
    private final ValueParser parser = new PropertyParser();

    @ParameterizedTest
    @MethodSource("parseProvider")
    void parse(String expressionString, Expression result) {
        assertEquals(result, parser.parse(expressionString));
    }

    private static Stream<Arguments> parseProvider(){
        return Stream.of(
                arguments("child.property", property("child.property")),
                arguments("child", property("child"))
        );
    }

    private static Expression property(String property){
        return new PropertyExpression(property);
    }

    @ParameterizedTest
    @CsvSource({
            "child.property,true",
            "child.property.another,true",
            "child,true",
            "child,true",
            "\"child.property\",false",
            "\"child.property.another\",false",
            "\"arbitrary sd 83 string\",false"
    })
    void pattern(String string, boolean matches) {
        assertEquals(matches, parser.pattern().matcher(string).matches());
    }
}