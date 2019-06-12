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

class IsEqualToTest {
    private final IsEqualTo isEqualTo = new IsEqualTo();

    @ParameterizedTest
    @MethodSource("doEvaluateProvider")
    void doEvaluate(Expression left, Expression right, boolean result) {
        isEqualTo.setLeft(left);
        isEqualTo.setRight(right);
        assertAll(
                () -> assertEquals(result, isEqualTo.evaluate()),
                () -> assertEquals(result, isEqualTo.evaluateToBoolean())
        );
    }

    private static Stream<Arguments> doEvaluateProvider() {
        return Stream.of(
                arguments(object(1), object(0), false),
                arguments(object(1), object(1), true),
                arguments(object(""), object(1), false),
                arguments(object(true), object(false), false),
                arguments(object(true), object(true), true),
                arguments(object(false), object(true), false),
                arguments(object(false), object(false), true),
                arguments(object(1), object(10), false)
        );
    }

    private static Expression object(Object object) {
        return new ObjectExpression(object);
    }
}