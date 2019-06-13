package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.PropertyExpression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.property;
import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.value;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PropertyPrinterTest {
    private final ValuePrinter printer = new PropertyPrinter();

    @ParameterizedTest
    @MethodSource("printProvider")
    void print(Expression expression, String result) {
        assertEquals(result, printer.print(expression));
    }

    private static Stream<Arguments> printProvider() {
        return Stream.of(
                arguments(property("child").build(), "child"),
                arguments(property("child.property").build(), "child.property")
        );
    }

    @ParameterizedTest
    @MethodSource("supportsProvider")
    void supports(Expression expression, boolean supports){
        assertEquals(supports, printer.supportsExpression(expression));
    }

    private static Stream<Arguments> supportsProvider(){
        return Stream.of(
                arguments(value(1).build(), false),
                arguments(value(new Object()).build(), false),
                arguments(value("a string").build(), false),
                arguments(property("child.property").build(), true)
        );
    }
}