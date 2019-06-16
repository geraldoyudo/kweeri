package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.operators.And;
import com.geraldoyudo.kweeri.core.operators.Or;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicQueryOperatorDefinitionsTest {
    private final BasicQueryOperatorDefinitions operatorDefinitions = new BasicQueryOperatorDefinitions()
            .defineOperator(new And(), "and")
            .defineOperator(new Or(), "or");

    @Test
    void getOperatorsPattern() {
        assertEquals("or|and", operatorDefinitions.getBinaryOperatorsPattern());
    }
}