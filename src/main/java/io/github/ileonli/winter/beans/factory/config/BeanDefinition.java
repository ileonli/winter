package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.PropertyValues;

import java.util.Objects;

public class BeanDefinition {

    private Class<?> beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition() {
    }

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this(beanClass, propertyValues, null, null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues, String initMethodName, String destroyMethodName) {
        this.beanClass = beanClass;
        this.propertyValues = Objects.requireNonNullElse(propertyValues, new PropertyValues());
        this.initMethodName = initMethodName;
        this.destroyMethodName = destroyMethodName;
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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BeanDefinition that = (BeanDefinition) o;
        return Objects.equals(beanClass, that.beanClass) &&
                Objects.equals(propertyValues, that.propertyValues) &&
                Objects.equals(initMethodName, that.initMethodName) &&
                Objects.equals(destroyMethodName, that.destroyMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass, propertyValues, initMethodName, destroyMethodName);
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                '}';
    }

}
