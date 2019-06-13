package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.operators.Operator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicQueryOperatorDefinitions {

    private Map<String, Long> operatorMap = new HashMap<>();
    private Map<Long, String> reverseMap = new HashMap<>();
    private Map<Long, Class<? extends Operator>> operatorClass = new HashMap<>();


    public BasicQueryOperatorDefinitions defineOperator(Operator operator, String string) {
        operatorMap.put(string, operator.operatorId());
        operatorClass.put(operator.operatorId(), operator.getClass());
        reverseMap.put(operator.operatorId(), string);
        return this;
    }

    public boolean isAnOperator(String string) {
        return operatorMap.containsKey(string);
    }

    public Optional<Long> toOperatorId(String string) {
        return Optional.ofNullable(operatorMap.get(string));
    }

    public Optional<String> toOperatorString(long operatorId) {
        return Optional.ofNullable(reverseMap.get(operatorId));
    }

    public String getOperatorsPattern() {
        StringBuilder builder = new StringBuilder();
        for (String operator : operatorMap.keySet()) {
            builder.append(operator);
            builder.append("|");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

    public Optional<Operator> createOperator(long id) {
        return Optional.ofNullable(operatorClass.get(id)).map(clazz -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException |
                    IllegalAccessException | InvocationTargetException ex) {
                throw new QueryProcessingException("Could not create query operator. Ensure that registered operator " +
                        "has a public no argument constructor");
            }
        });
    }
}
