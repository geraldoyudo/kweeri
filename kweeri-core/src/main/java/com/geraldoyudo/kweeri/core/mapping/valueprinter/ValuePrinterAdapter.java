package com.geraldoyudo.kweeri.core.mapping.valueprinter;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.UnrecognizedValueExpression;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class ValuePrinterAdapter {

    private List<ValuePrinter> printers = new LinkedList<>();

    public ValuePrinterAdapter addPrinters(ValuePrinter... printers) {
        this.printers.addAll(asList(printers));
        return this;
    }

    public String print(Expression expression) throws UnrecognizedValueExpression {
        for (ValuePrinter printer : printers) {
            if (printer.supportsExpression(expression)) {
                return printer.print(expression);
            }
        }
        throw new UnrecognizedValueExpression("Could not recognize expression");
    }

}
