package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.property;
import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.value;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringPrinterTest {
    private final ValuePrinter printer = new StringPrinter();

    @ParameterizedTest
    @MethodSource("printProvider")
    void print(Expression expression, String result) {
        assertEquals(result, printer.print(expression));
    }

    private static Stream<Arguments> printProvider() {
        return Stream.of(
                arguments(value("a string").build(), "'a string'"),
                arguments(value("text").build(), "'text'")
        );
    }

    @ParameterizedTest
    @MethodSource("supportsProvider")
    void supports(Expression expression, boolean supports) {
        assertEquals(supports, printer.supportsExpression(expression));
    }

    private static Stream<Arguments> supportsProvider() {
        return Stream.of(
                arguments(value(1).build(), false),
                arguments(value(new Object()).build(), false),
                arguments(value("a string").build(), true),
                arguments(property("child.property").build(), false)
        );
    }

    @Test
    public void shouldUseDoubleQuotesIfDoubleQuotesIsSet() {
        ValuePrinter valuePrinter = new StringPrinter(true);
        assertEquals("\"test\"", valuePrinter.print(value("test").build()));
    }
}