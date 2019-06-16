package com.geraldoyudo.kweeri.core.mapping;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicQueryExpressionDefinitionTest {

    public static final String EMPTY_MESSAGE = "opening and closing cannot be null or empty";

    @Test
    public void defaultShouldUseSquareBrackets() {
        BasicQueryExpressionDefinition expressionDefinition = new BasicQueryExpressionDefinition();
        assertOpeningAndClosingAreCorrect(expressionDefinition, "[", "]");
    }

    private void assertOpeningAndClosingAreCorrect(BasicQueryExpressionDefinition expressionDefinition, String opening,
                                                   String closing) {
        assertAll(
                () -> assertTrue(expressionDefinition.openingEquals(opening)),
                () -> assertTrue(expressionDefinition.closingEquals(closing))
        );
    }

    @Test
    public void changingOpeningAndClosingShouldReflect() {
        BasicQueryExpressionDefinition expressionDefinition = new BasicQueryExpressionDefinition()
                .bracket("(", ")");
        assertOpeningAndClosingAreCorrect(expressionDefinition, "(", ")");
    }

    @Test
    public void usingOneCharacterForOpeningAndClosingShouldResultInIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new BasicQueryExpressionDefinition()
                        .bracket("|", "|"),
                EMPTY_MESSAGE
        );
    }

    @Test
    public void settingOpeningToNullShouldResultInIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new BasicQueryExpressionDefinition()
                        .bracket(null, ""),
                EMPTY_MESSAGE
        );
    }

    @Test
    public void settingClosingToNullShouldResultInIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new BasicQueryExpressionDefinition()
                        .bracket("{", null),
                EMPTY_MESSAGE
        );
    }

    @Test
    public void settingOpeningToEmptyShouldResultInIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new BasicQueryExpressionDefinition()
                        .bracket("", ""),
                EMPTY_MESSAGE
        );
    }

    @Test
    public void settingClosingToEmptyShouldResultInIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new BasicQueryExpressionDefinition()
                        .bracket("{", ""),
                EMPTY_MESSAGE
        );
    }

}