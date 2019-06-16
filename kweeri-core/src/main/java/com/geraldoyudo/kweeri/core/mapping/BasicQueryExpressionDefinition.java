package com.geraldoyudo.kweeri.core.mapping;

import org.apache.commons.lang3.StringUtils;

public class BasicQueryExpressionDefinition {
    private String opening = "[";
    private String closing = "]";

    public BasicQueryExpressionDefinition bracket(String opening, String closing) {
        if (StringUtils.isEmpty(opening) || StringUtils.isEmpty(closing)) {
            throw new IllegalArgumentException("opening and closing cannot be null or blank");
        }
        if (closing.equals(opening)) {
            throw new IllegalArgumentException("closing cannot be same as opening");
        }
        this.opening = opening;
        this.closing = closing;
        return this;
    }

    boolean openingEquals(String opening) {
        return this.opening.equals(opening);
    }

    boolean closingEquals(String closing) {
        return this.closing.equals(closing);
    }

    public String getOpening() {
        return opening;
    }

    public String getClosing() {
        return closing;
    }
}
