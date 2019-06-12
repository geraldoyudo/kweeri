package com.geraldoyudo.kweeri.core;

import org.junit.jupiter.api.Test;

import static com.geraldoyudo.kweeri.core.ExpressionBuilder.expression;
import static com.geraldoyudo.kweeri.core.ExpressionBuilder.value;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionBuilderTest {

    @Test
    public void oneShouldEvaluateToTrue() {
        assertEquals(true,
                value(1)
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void minusOneShouldEvaluateToFalse() {
        assertEquals(false,
                value(-1)
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void oneAndMinusOneShouldEvaluateToFalse() {
        assertEquals(false,
                value(-1)
                        .and(value(1))
                        .build().evaluate()
        );
    }

    @Test
    public void oneAndOneShouldEvaluateToTrue() {
        assertEquals(true,
                value(1)
                        .and(value(1))
                        .build().evaluate()
        );
    }

    @Test
    public void trueAndFalseShouldEvaluateToFalse() {
        assertEquals(false,
                value(true).and(value(false))
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void trueAndTrueAndTrueShouldEvaluateToTrue() {
        assertEquals(true,
                value(true).and(value(true)).and(value(true))
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void falseAndFalseAndFalseShouldEvaluateToFalse() {
        assertEquals(false,
                value(false).and(value(false)).and(value(false))
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void trueAndFalseAndFalseShouldEvaluateToFalse() {
        assertEquals(false,
                value(true).and(value(false)).and(value(false))
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void twoEqualsTwoAndFourEqualsFourShouldBeTrue() {
        assertEquals(true,
                value(2).equalTo(value(2)).and(value(4).equalTo(value(4)))
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void withBracketTwoEqualsTwoAndFourEqualsFourShouldBeTrue() {
        assertEquals(true,
                expression(value(2).equalTo(value(2))).and(value(4).equalTo(value(4)))
                        .build().evaluateToBoolean()
        );
    }

    @Test
    public void oneOrMinusOneShouldEvaluateToTrue() {
        assertEquals(true,
                value(1)
                        .or(value(-1))
                        .build().evaluate()
        );
    }

    @Test
    public void twoEqualToFiveOrThreeEqualToThreeShouldEvaluateToTrue() {
        assertEquals(true,
                value(2).equalTo(value(5))
                        .or(value(3).equalTo(value(3)))
                        .build().evaluate()
        );
    }

    @Test
    public void twoEqualToFiveOrThreeEqualToMinusThreeShouldEvaluateToFalse() {
        assertEquals(false,
                value(2).equalTo(value(5))
                        .or(value(3).equalTo(value(-3)))
                        .build().evaluate()
        );
    }
}
