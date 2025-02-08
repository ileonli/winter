package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValues;

import java.util.Objects;

public class BeanDefinition {

    public static String SCOPE_SINGLETON = "singleton";

    public static String SCOPE_PROTOTYPE = "prototype";

    private Class<?> beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    private String scope;

    public BeanDefinition() {
        this(null);
    }

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this(beanClass, propertyValues, null, null, SCOPE_SINGLETON);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues,
                          String initMethodName, String destroyMethodName, String scope) {
        this.beanClass = beanClass;
        this.propertyValues = Objects.requireNonNullElse(propertyValues, new PropertyValues());
        this.initMethodName = initMethodName;
        this.destroyMethodName = destroyMethodName;

        if (!scope.equals(SCOPE_SINGLETON) && !scope.equals(SCOPE_PROTOTYPE)) {
            throw new BeansException("Bean scope must be: " + SCOPE_SINGLETON + " or " + SCOPE_PROTOTYPE);
        }
        this.scope = scope;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        if (!scope.equals(SCOPE_SINGLETON) && !scope.equals(SCOPE_PROTOTYPE)) {
            throw new BeansException("Bean scope must be: " + SCOPE_SINGLETON + " or " + SCOPE_PROTOTYPE);
        }
        this.scope = scope;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BeanDefinition that = (BeanDefinition) o;
        return Objects.equals(beanClass, that.beanClass) &&
                Objects.equals(propertyValues, that.propertyValues) &&
                Objects.equals(initMethodName, that.initMethodName) &&
                Objects.equals(destroyMethodName, that.destroyMethodName) &&
                Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass, propertyValues, initMethodName, destroyMethodName, scope);
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                ", initMethodName='" + initMethodName + '\'' +
                ", destroyMethodName='" + destroyMethodName + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }

}
