package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.UnrecognizedValueExpression;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.ValueParserAdapter;
import com.geraldoyudo.kweeri.core.mapping.valueprinter.ValuePrinterAdapter;
import com.geraldoyudo.kweeri.core.operators.Operator;

import java.util.Scanner;
import java.util.regex.Pattern;

public class BasicQuerySerializer implements QuerySerializer, QueryDeserializer {
    static Pattern WHITE_SPACE_NOT_IN_QUOTES = Pattern.compile("\\s+(?=(?:[^\\'\"]*[\\'\"][^\\'\"]*[\\'\"])*[^\\'\"]*$)");
    private BasicQueryExpressionDefinition expressionDefinition = new BasicQueryExpressionDefinition();

    private BasicQueryOperatorDefinitions operatorDefinitions = new BasicQueryOperatorDefinitions();

    private ValueParserAdapter valueParserAdapter = new ValueParserAdapter();

    private ValuePrinterAdapter valuePrinterAdapter = new ValuePrinterAdapter();

    public BasicQueryExpressionDefinition getExpressionDefinition() {
        return expressionDefinition;
    }

    public void setExpressionDefinition(BasicQueryExpressionDefinition expressionDefinition) {
        this.expressionDefinition = expressionDefinition;
    }

    public BasicQueryOperatorDefinitions getOperatorDefinitions() {
        return operatorDefinitions;
    }

    public void setOperatorDefinitions(BasicQueryOperatorDefinitions operatorDefinitions) {
        this.operatorDefinitions = operatorDefinitions;
    }

    public void setValueParserAdapter(ValueParserAdapter valueParserAdapter) {
        this.valueParserAdapter = valueParserAdapter;
    }

    public void setValuePrinterAdapter(ValuePrinterAdapter valuePrinterAdapter) {
        this.valuePrinterAdapter = valuePrinterAdapter;
    }

    @Override
    public Expression serialize(String queryString) {
        String formattedQueryString = formatQueryString(queryString);
        Scanner scanner = new Scanner(formattedQueryString);
        scanner.useDelimiter(" ");
        Expression firstExpression = getExpression(scanner);
        String operatorsPattern = operatorDefinitions.getOperatorsPattern();
        while (scanner.hasNext(operatorsPattern)) {
            String operatorPattern = scanner.next(operatorsPattern);
            long operatorId = operatorDefinitions.toOperatorId(operatorPattern)
                    .orElseThrow(() -> new IllegalArgumentException("invalid operation"));
            Operator operator = getOperatorFromId(operatorId);
            Expression secondExpression = getExpression(scanner);
            operator.setLeft(firstExpression);
            operator.setRight(secondExpression);
            firstExpression = operator;
        }
        return firstExpression;
    }

    String formatQueryString(String queryString) {
        Pattern openingPattern = Pattern.compile(String.format("([\\\\%s])", expressionDefinition.getOpening()));
        Pattern closingPattern = Pattern.compile(String.format("([\\\\%s])", expressionDefinition.getClosing()));
        return queryString
                .replaceAll(String.format("\\b(%s)\\b", operatorDefinitions.getOperatorsPattern()), " $1 ")
                .replaceAll(openingPattern.pattern(), " $1 ")
                .replaceAll(closingPattern.pattern(), " $1 ")
                .replaceAll(WHITE_SPACE_NOT_IN_QUOTES.pattern(), " ")
                .trim();
    }

    private Expression getExpression(Scanner scanner) {
        Expression firstExpression;
        Pattern openingPattern = Pattern.compile(String.format("[\\\\%s]", expressionDefinition.getOpening()));
        if (scanner.hasNext(openingPattern)) {
            String subExpression = getSubExpression(scanner);
            scanner.useDelimiter(" ");
            firstExpression = serialize(subExpression);
        } else {
            Pattern valuePattern = valueParserAdapter.getCombinedPattern();
            if (scanner.hasNext(valuePattern)) {
                firstExpression = getExpressionFromValue(scanner.next(valuePattern));
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
        }
        return firstExpression;
    }


    String getSubExpression(Scanner scanner) {
        int count = 0;
        Pattern openingPattern = Pattern.compile(String.format("[\\\\%s]", expressionDefinition.getOpening()));
        Pattern closingPattern = Pattern.compile(String.format("[\\\\%s]", expressionDefinition.getClosing()));
        scanner.next(openingPattern);
        scanner.useDelimiter("");
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (openingPattern.matcher(token).matches()) {
                count++;
            }
            if (closingPattern.matcher(token).matches()) {
                if (count == 0) {
                    return builder.toString();
                }
                count--;
            }
            builder.append(token);
        }
        return builder.toString();
    }

    private Expression getExpressionFromValue(String valueExpression) {
        try {
            return valueParserAdapter.parse(valueExpression);
        } catch (UnrecognizedValueExpression ex) {
            throw new QueryProcessingException("Illegal value expression");
        }
    }

    private Operator getOperatorFromId(long id) {
        return operatorDefinitions.createOperator(id)
                .orElseThrow(() -> new QueryProcessingException("Unknown query operator"));
    }

    @Override
    public String deserialize(Expression expression) {
        StringBuilder builder = new StringBuilder();
        deserialize(expression, builder);
        return builder.toString();
    }

    private void deserialize(Expression expression, StringBuilder stringBuilder) {
        try {
            if (expression instanceof Operator) {
                Operator operator = (Operator) expression;
                appendExpression(operator.getLeft(), stringBuilder);
                stringBuilder.append(" ");
                stringBuilder.append(
                        operatorDefinitions.toOperatorString(operator.operatorId())
                                .orElseThrow(() -> new QueryProcessingException("cannot recognize operator to print"))
                );
                stringBuilder.append(" ");
                appendExpression(operator.getRight(), stringBuilder);
            } else {
                stringBuilder.append(valuePrinterAdapter.print(expression));
            }
        } catch (UnrecognizedValueExpression ex) {
            throw new QueryProcessingException("unrecognized expression. cannot print");
        }
    }

    private void appendExpression(Expression expression, StringBuilder builder) {
        if (expression instanceof Operator) {
            builder.append(expressionDefinition.getOpening()).append(" ");
            deserialize(expression, builder);
            builder.append(" ").append(expressionDefinition.getClosing());
        } else {
            deserialize(expression, builder);
        }
    }
}
