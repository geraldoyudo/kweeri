package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringParserTest {
    private ValueParser stringParser = new StringParser();

    @Test
    void matches() {
        Pattern pattern = stringParser.pattern();
        assertAll(
                () -> assertTrue(pattern.matcher("'table'").matches()),
                () -> assertTrue(pattern.matcher("'table and chair '").matches()),
                () -> assertTrue(pattern.matcher("'table and chair and bag'").matches()),
                () -> assertTrue(pattern.matcher("\"table and chair and bag\"").matches()),
                () -> assertTrue(pattern.matcher("\"table and chair \"").matches()),
                () -> assertTrue(pattern.matcher("\"table \"").matches()),
                () -> assertFalse(pattern.matcher("table ").matches()),
                () -> assertFalse(pattern.matcher("23 ").matches()),
                () -> assertFalse(pattern.matcher("23").matches()),
                () -> assertFalse(pattern.matcher("abd and de").matches())
        );
    }

    @ParameterizedTest
    @MethodSource("parsingProvider")
    void parsing(String expressionString, Expression expression){
        assertEquals(expression, stringParser.parse(expressionString));
    }

    private static Stream<Arguments> parsingProvider(){
        return Stream.of(
                arguments("'antelope'", string("antelope")),
                arguments("'this is a boy'", string("this is a boy")),
                arguments("\"queen\"", string("queen"))
        );
    }

    private static ObjectExpression string(String string){
        return new ObjectExpression(string);
    }
}