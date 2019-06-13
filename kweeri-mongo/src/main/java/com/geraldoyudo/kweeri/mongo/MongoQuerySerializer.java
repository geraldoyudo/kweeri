package com.geraldoyudo.kweeri.mongo;

import com.geraldoyudo.kweeri.core.mapping.BasicQuerySerializer;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.BooleanParser;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.IntegerParser;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.PropertyParser;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.StringParser;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.ObjectPrinter;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.PropertyPrinter;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.StringPrinter;
import com.geraldoyudo.kweeri.core.operators.And;
import com.geraldoyudo.kweeri.core.operators.Equals;

public class MongoQuerySerializer extends BasicQuerySerializer {

    public MongoQuerySerializer() {
        getExpressionDefinition().bracket("(", ")");
        getOperatorDefinitions()
                .defineOperator(new And(), "and")
                .defineOperator(new Equals(), "equals");
        getValueParserAdapter()
                .addParsers(new StringParser(), new BooleanParser(), new IntegerParser(), new PropertyParser());
        getValuePrinterAdapter()
                .addPrinters(new StringPrinter(), new PropertyPrinter(), new ObjectPrinter());
    }
}
