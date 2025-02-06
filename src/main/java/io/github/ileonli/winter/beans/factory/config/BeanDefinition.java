package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.PropertyValues;

import java.util.Objects;

public class BeanDefinition {

    private Class<?> beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition() {
    }

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = Objects.requireNonNullElse(propertyValues, new PropertyValues());
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BeanDefinition that = (BeanDefinition) o;
        return Objects.equals(beanClass, that.beanClass) && Objects.equals(propertyValues, that.propertyValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass, propertyValues);
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                '}';
    }

}
