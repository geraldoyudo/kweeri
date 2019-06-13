package com.geraldoyudo.kweeri.core.mapping.valueparsers;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueParserAdapterTest {

    @Test
    void getCombinedPattern() {
        ValueParserAdapter adapter = new ValueParserAdapter();
        adapter.addParsers(new StringParser(), new IntegerParser());
        Pattern combinePattern = adapter.getCombinedPattern();
        assertTrue(combinePattern.matcher("23").matches());
        assertTrue(combinePattern.matcher("'something '").matches());
        assertTrue(combinePattern.matcher("\"something \"").matches());
        assertFalse(combinePattern.matcher("3232.3232").matches());
    }
}