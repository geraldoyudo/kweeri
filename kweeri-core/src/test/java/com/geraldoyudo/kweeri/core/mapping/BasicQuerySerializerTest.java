package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.*;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.ObjectPrinter;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.PropertyPrinter;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.StringPrinter;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.ValuePrinterAdapter;
import com.geraldoyudo.kweeri.core.operators.And;
import com.geraldoyudo.kweeri.core.operators.IsEqualTo;
import com.geraldoyudo.kweeri.core.operators.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.geraldoyudo.kweeri.core.expression.ExpressionBuilder.*;
import static com.geraldoyudo.kweeri.core.mapping.BasicQuerySerializer.WHITE_SPACE_NOT_IN_QUOTES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BasicQuerySerializerTest {
    private BasicQuerySerializer basicQuerySerializer = new BasicQuerySerializer();

    @BeforeEach
    public void setUp() {
        basicQuerySerializer.setExpressionDefinition(
                new BasicQueryExpressionDefinition().bracket("(", ")"));
        basicQuerySerializer.setOperatorDefinitions(new BasicQueryOperatorDefinitions()
                .defineOperator(new And(), "and")
                .defineOperator(new Or(), "or")
                .defineOperator(new IsEqualTo(), "=")
        );
        basicQuerySerializer.setValueParserAdapter(
                new ValueParserAdapter()
                        .addParsers(new StringParser(), new BooleanParser(), new IntegerParser(), new PropertyParser())
        );
        basicQuerySerializer.setValuePrinterAdapter(
                new ValuePrinterAdapter()
                        .addPrinters(new StringPrinter(), new PropertyPrinter(), new ObjectPrinter())
        );
    }

    @ParameterizedTest
    @MethodSource("serializeProvider")
    void serialize(String queryString, Expression result) {
        assertEquals(result, basicQuerySerializer.serialize(queryString));
    }

    private static Stream<Arguments> serializeProvider() {
        return Stream.of(
                arguments("1 = 1", value(1).equalTo(value(1)).build()),
                arguments("1=1", value(1).equalTo(value(1)).build()),
                arguments("true", value(true).build()),
                arguments("false", value(false).build()),
                arguments("true", value(true).build()),
                arguments("'three' = 'four'", value("three").equalTo(value("four")).build()),
                arguments("(color = 'red') and (nationality = 'nigerian')",
                        expression(property("color").equalTo(value("red")))
                                .and(expression(property("nationality").equalTo(value("nigerian"))))
                                .build()
                ),
                arguments("(color = 'red') and (nationality =     'nigerian')",
                        expression(property("color").equalTo(value("red")))
                                .and(expression(property("nationality").equalTo(value("nigerian"))))
                                .build()
                ),
                arguments("(color = 'red') and (nationality = 'nigerian') and (gender = 'female')",
                        expression(property("color").equalTo(value("red")))
                                .and(expression(property("nationality").equalTo(value("nigerian"))))
                                .and(expression(property("gender").equalTo(value("female"))))
                                .build()
                ),
                arguments("(color =   'red')and (nationality =  'nigerian') and (gender =    'female')",
                        expression(property("color").equalTo(value("red")))
                                .and(expression(property("nationality").equalTo(value("nigerian"))))
                                .and(expression(property("gender").equalTo(value("female"))))
                                .build()
                ),
                arguments("((color     =  'red')  and (nationality = 'nigerian')) and (gender =   'female')",
                        expression(
                                expression(property("color").equalTo(value("red"))).and(
                                        expression(property("nationality").equalTo(value("nigerian")))
                                )
                        ).and(
                                expression(property("gender").equalTo(value("female")))
                        )
                                .build()
                ),
                arguments("1", value(1).build())
        );
    }

    @Test
    public void replaceSpacesNotInQuotes() {
        String string = "this is a spacy spacy spacy \"sp a c y\" and 'spa cy' text";
        assertEquals("thisisaspacyspacyspacy\"sp a c y\"and'spa cy'text",
                string.replaceAll(WHITE_SPACE_NOT_IN_QUOTES.pattern(), ""));
    }

    @ParameterizedTest
    @CsvSource({
            "apple,apple",
            "'1    and    5','1 and 5",
            "((one and five)),( ( one and five ) )",
            "(     (one    and five)),( ( one and five ) )",
            "color    =  \"red\",color = \"red\"",
            "(    color =    \"red\") and (nationality = \"nigerian\"    ),( color = \"red\" ) and ( nationality = \"nigerian\" )",
    })
    public void testFormatQueryString(String expression, String result) {
        assertEquals(result.trim(), basicQuerySerializer.formatQueryString(expression));
    }

    @ParameterizedTest
    @MethodSource("deSerializeProvider")
    void deSerialize(String result, Expression expression) {
        assertEquals(result, basicQuerySerializer.deserialize(expression));
    }

    private static Stream<Arguments> deSerializeProvider() {
        return Stream.of(
                arguments("1 = 1", value(1).equalTo(value(1)).build()),
                arguments("( color = 'red' ) and ( nationality = 'nigerian' )", expression(
                        property("color").equalTo(value("red"))
                ).and(
                        property("nationality").equalTo(value("nigerian"))
                        ).build()
                ),
                arguments("color = 'red'", property("color").equalTo(value("red")).build())
        );
    }
}