package com.geraldoyudo.kweeri.core.expression;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PropertyExpressionTest {

    @Test
    void evaluateWithSubProperty() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("value1", 3);
        PropertyExpression expression = new PropertyExpression("value1");
        assertEquals(3, expression.evaluate(objectMap));
    }

    @Test
    void givenEmptySubPropertyShouldReturnNull() {
        PropertyExpression expression = new PropertyExpression("value1");
        assertNull(expression.evaluate());
    }

    @Test
    void evaluateWithNestedProperty() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("value1", 3);
        Map<String, Object> parent = new HashMap<>();
        parent.put("child", objectMap);
        PropertyExpression expression = new PropertyExpression("child.value1");
        assertEquals(3, expression.evaluate(parent));
    }

    @Test
    void givenEmptyNestedPropertyShouldReturnNull() {
        PropertyExpression expression = new PropertyExpression("child.value1");
        assertNull(expression.evaluate());
    }
}