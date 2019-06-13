package com.geraldoyudo.kweeri.mongo;

import com.geraldoyudo.kweeri.core.converters.QueryNotSupportedException;
import com.geraldoyudo.kweeri.core.expression.Expression;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.property;
import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.value;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MongoQueryConverterTest {
    private final MongoQueryConverter mongoQueryConverter = new MongoQueryConverter() {
        @Override
        protected String transformProperty(String property) {
            if (!property.equals("username")) {
                return "properties." + property;
            } else {
                return property;
            }
        }
    };

    @ParameterizedTest
    @MethodSource("convertExpressionToQueryProvider")
    void convertExpressionToQuery(JSONObject result, Expression expression) {
        assertEquals(result.toString(), mongoQueryConverter.convertExpressionToQuery(expression));
    }

    private static Stream<Arguments> convertExpressionToQueryProvider() {
        return Stream.of(
                arguments(
                        new JSONObject()
                                .put("properties.color", "red")
                                .put("username", "user"),
                        property("color").equalTo(value("red"))
                                .and(property("username").equalTo(value("user"))).build()
                ),
                arguments(
                        new JSONObject()
                                .put("properties.color", "red")
                                .put("properties.nationality", "nigerian")
                                .put("properties.child.property", "value"),
                        property("color").equalTo(value("red"))
                                .and(property("nationality").equalTo(value("nigerian")))
                                .and(property("child.property").equalTo(value("value")))
                                .build()
                )
        );
    }

    @Test
    public void shouldThrowQueryNotSupportedWhenPropertyIsDuplicated() {
        assertThrows(QueryNotSupportedException.class,
                () -> mongoQueryConverter.convertExpressionToQuery(
                        property("color").equalTo(value("red"))
                                .and(property("color").equalTo(value("green"))).build()
                )
        );
    }

    @Test
    public void shouldThrowQueryNotSupportedWhenAnyOperatorApartFrmAndAndEqualsArePresent() {
        assertThrows(QueryNotSupportedException.class,
                () -> mongoQueryConverter.convertExpressionToQuery(
                        property("color").equalTo(value("red"))
                                .or(property("nigeria").equalTo(value("dirty"))).build()
                )
        );
    }
}