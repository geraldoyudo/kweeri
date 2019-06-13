package com.geraldoyudo.kweeri.mongo;

import com.geraldoyudo.kweeri.core.converters.QueryConverter;
import com.geraldoyudo.kweeri.core.converters.QueryNotSupportedException;
import com.geraldoyudo.kweeri.core.expression.Expression;
import com.geraldoyudo.kweeri.core.expression.ObjectExpression;
import com.geraldoyudo.kweeri.core.expression.PropertyExpression;
import com.geraldoyudo.kweeri.core.operators.And;
import com.geraldoyudo.kweeri.core.operators.Equals;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MongoQueryConverter implements QueryConverter<String> {

    public String convertExpressionToQuery(Expression expression) throws QueryNotSupportedException {
        JSONObject object = new JSONObject();
        List<String> propertiesInQuery = new ArrayList<>();
        convertExpressionToQuery(expression, object, propertiesInQuery);
        return object.toString();
    }

    private void convertExpressionToQuery(Expression expression, JSONObject object, List<String> propertiesInQuery) {
        if (expression instanceof Equals) {
            Expression left = ((Equals) expression).getLeft();
            Expression right = ((Equals) expression).getRight();
            if (left instanceof PropertyExpression && right instanceof ObjectExpression) {
                addPropertyExpressionAndValueQuery((PropertyExpression) left, (ObjectExpression) right,
                        object, propertiesInQuery);
            }
            if (right instanceof PropertyExpression && left instanceof ObjectExpression) {
                addPropertyExpressionAndValueQuery((PropertyExpression) right, (ObjectExpression) left,
                        object, propertiesInQuery);
            }
        } else if (expression instanceof And) {
            convertExpressionToQuery(((And) expression).getLeft(), object, propertiesInQuery);
            convertExpressionToQuery(((And) expression).getRight(), object, propertiesInQuery);
        } else {
            throw new QueryNotSupportedException("Query is not supported");
        }
    }

    private void addPropertyExpressionAndValueQuery(PropertyExpression propertyExpression,
                                                    ObjectExpression objectExpression,
                                                    JSONObject object, List<String> propertiesInQuery) {
        if (propertiesInQuery.contains(propertyExpression.getProperty())) {
            throw new QueryNotSupportedException("Property should only occur once in expression");
        }
        propertiesInQuery.add(propertyExpression.getProperty());
        object.put(transformProperty(propertyExpression.getProperty()), objectExpression.getObject());
    }

    protected String transformProperty(String property) {
        return property;
    }
}
