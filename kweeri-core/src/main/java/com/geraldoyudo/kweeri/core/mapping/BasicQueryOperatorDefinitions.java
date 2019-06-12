package com.geraldoyudo.kweeri.core.mapping;

import com.geraldoyudo.kweeri.core.operators.Operator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicQueryOperatorDefinitions {
    private Map<String, Long> operatorMap = new HashMap<>();

    public BasicQueryOperatorDefinitions defineOperator(long operatorId, String string) {
        operatorMap.put(string, operatorId);
        return this;
    }

    public BasicQueryOperatorDefinitions defineOperator(Operator operator, String string) {
        operatorMap.put(string, operator.operatorId());
        return this;
    }

    public boolean isAnOperator(String string) {
        return operatorMap.containsKey(string);
    }

    public Optional<Long> toOperatorId(String string) {
        return Optional.ofNullable(operatorMap.get(string));
    }
}
