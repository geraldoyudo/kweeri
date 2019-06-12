package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.value;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BasicQuerySerializerTest {
    private BasicQuerySerializer basicQuerySerializer = new BasicQuerySerializer();


    @ParameterizedTest
    @MethodSource("serializeProvider")
    void serialize(String queryString, Expression result) {
        assertEquals(result, basicQuerySerializer.serialize(queryString));
    }

    private static Stream<Arguments> serializeProvider(){
        return Stream.of(
                arguments("1 = 1", value(1).equalTo(value(1)).build()),
                arguments("1=1", value(1).equalTo(value(1)).build()),
                arguments("true", value(true).build()),
                arguments("true", value(true).build()),
                arguments("1", value(1).build())
        );
    }
}