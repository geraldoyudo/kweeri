package com.geraldoyudo.kweeri.mongo;

import org.junit.jupiter.api.Test;

import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.property;
import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.value;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MongoQuerySerializerTest {
    private MongoQuerySerializer querySerializer = new MongoQuerySerializer();

    @Test
    public void testQuerySerialization() {
        assertEquals("( color equals 'red' ) and ( nationality equals 'nigerian' )",
                querySerializer.serialize(
                        property("color").equalTo(value("red"))
                                .and(property("nationality").equalTo(value("nigerian"))).build()
                )
        );
    }

    @Test
    public void testQueryDeserialization() {
        assertEquals(property("color").equalTo(value("red"))
                        .and(property("nationality").equalTo(value("nigerian"))).build(),
                querySerializer.deSerialize(
                        "( color equals 'red' ) and ( nationality equals 'nigerian' )"
                )
        );
    }

}