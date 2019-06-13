package com.geraldoyudo.kweeri.core.expression;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class PropertyExpression implements Expression<Object> {

    private String property;

    public PropertyExpression(String property) {
        this.property = property;
    }

    @Override
    public Object evaluate(Object context) {
        try {
            return PropertyUtils.getNestedProperty(context, property);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NestedNullException ex) {
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PropertyExpression that = (PropertyExpression) object;
        return Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(property);
    }
}
