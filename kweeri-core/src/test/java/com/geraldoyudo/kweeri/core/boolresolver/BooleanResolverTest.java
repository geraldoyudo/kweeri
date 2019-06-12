package com.geraldoyudo.kweeri.core.boolresolver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BooleanResolverTest {
    private BooleanResolver resolver = BooleanResolver.instance();

    @ParameterizedTest
    @MethodSource("toBooleanProvider")
    void resolve(Object object, Boolean value) {
        assertEquals(value, resolver.resolve(object));
    }

    @Test
    public void zeroEqualtozero(){
        Integer integer = 0;
        assertEquals(0, integer.doubleValue());
    }

    private static Stream<Arguments> toBooleanProvider() {
        return Stream.of(
                arguments(true, true),
                arguments(false, false),
                arguments("", false),
                arguments("sfaslkfja", true),
                arguments("apple", true),
                arguments("true", true),
                arguments(null, false),
                arguments(0, false),
                arguments(1, true),
                arguments(10, true),
                arguments(10L, true),
                arguments(10F, true),
                arguments(10D, true),
                arguments(BigInteger.TEN, true),
                arguments(BigInteger.ZERO, false),
                arguments(new Object(), true),
                arguments("false", false)
        );
    }
}