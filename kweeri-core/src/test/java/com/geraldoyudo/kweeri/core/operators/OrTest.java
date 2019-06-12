package com.geraldoyudo.kweeri.core.operators;

import com.geraldoyudo.kweeri.core.Expression;
import com.geraldoyudo.kweeri.core.ObjectExpression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OrTest {
    private final Or or = new Or();

    @ParameterizedTest
    @MethodSource("doEvaluateProvider")
    void doEvaluate(Expression left, Expression right, boolean result) {
        or.setLeft(left);
        or.setRight(right);
        assertAll(
                () -> assertEquals(result, or.evaluate()),
                () -> assertEquals(result, or.evaluateToBoolean())
        );
    }

    private static Stream<Arguments> doEvaluateProvider() {
        return Stream.of(
                arguments(object(1), object(0), true),
                arguments(object(1), object(1), true),
                arguments(object(""), object(1), true),
                arguments(object(true), object(false), true),
                arguments(object(true), object(true), true),
                arguments(object(false), object(true), true),
                arguments(object(false), object(false), false),
                arguments(object(1), object(10), true)
        );
    }

    private static Expression object(Object object) {
        return new ObjectExpression(object);
    }
}