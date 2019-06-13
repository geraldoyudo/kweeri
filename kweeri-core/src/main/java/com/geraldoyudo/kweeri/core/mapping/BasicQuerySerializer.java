package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.UnrecognizedValueExpression;
import com.geraldoyudo.kweeri.core.mapping.valueparsers.ValueParserAdapter;
import com.geraldoyudo.kweeri.core.operators.Operator;

import java.util.Scanner;
import java.util.regex.Pattern;

public class BasicQuerySerializer implements QuerySerializer {
    static Pattern WHITE_SPACE_NOT_IN_QUOTES = Pattern.compile("\\s+(?=(?:[^\\'\"]*[\\'\"][^\\'\"]*[\\'\"])*[^\\'\"]*$)");
    static Pattern PROPERTY_PATTERN = Pattern.compile("[A-Za-z0-9\\.]+");
    private BasicQueryExpressionDefinition expressionDefinition = new BasicQueryExpressionDefinition();

    private BasicQueryOperatorDefinitions operatorDefinitions = new BasicQueryOperatorDefinitions();

    private ValueParserAdapter valueParserAdapter = new ValueParserAdapter();

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

    @Override
    public Expression serialize(String queryString) {
        String formattedQueryString = formatQueryString(queryString);
        /*
        1. get first character
        2. if first character is bracket,
            a. get the whole string till the ending of the bracket
            b. call serialize recursively to get expression
        3. if first token delimited by an operator is a value expression
            a. serialize value to value expression
        4. if first token delimited by an operator is a property expression
            a. serialize property expression
        5. if next operator exists read next operator token and create operator
        6. if next character is a bracket
            a. get the whole string till the ending of the brket
            b. call serialize recursively to get the expression
        7. if next token till operator delimeter is a value expression
            a. serialize value to value expression
        8. if next token is property expression
            a. serialize to property expression
        9. set left and right of operator to first and second expression
        10. set resultExpression to the operator result expression
        11. Go to step 5
         */
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
}
