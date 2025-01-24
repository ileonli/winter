package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.*;
import io.github.ileonli.winter.factory.config.BeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private final InstantiationStrategy instantiationStrategy;

    public AbstractAutowireCapableBeanFactory() {
        this.instantiationStrategy = new SimpleInstantiationStrategy();
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object instantiate = instantiationStrategy.instantiate(beanDefinition);
        applyPropertyValues(beanName, instantiate, beanDefinition);
        addSingleton(beanName, instantiate);
        return instantiate;
    }

    protected void applyPropertyValues(String beanName, Object existBean, BeanDefinition beanDefinition) {
        if (existBean == null || beanDefinition == null) {
            throw new IllegalArgumentException("Bean instance or BeanDefinition must not be null");
        }

        PropertyValues pvs = beanDefinition.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        Class<?> beanClass = beanDefinition.getBeanClass();
        for (PropertyValue pv : pvs.getPropertyValues()) {
            try {
                applyPropertyValue(existBean, beanClass, pv);
            } catch (BeansException e) {
                throw new BeansException("Failed to apply property '" + pv.getName() + "' to bean '" + beanName + "'", e);
            }
        }
    }

    private void applyPropertyValue(Object bean, Class<?> beanClass, PropertyValue pv) {
        String fieldName = pv.getName();
        Object value = pv.getValue();

        if (value == null) {
            throw new BeansException("Property value for field '" + fieldName + "' is null");
        }

        try {
            Field field = beanClass.getDeclaredField(fieldName);
            Class<?> fieldType = field.getType();

            String setMethodName = BeanUtils.fieldNameToSetMethodName(fieldName);
            Method setMethod = beanClass.getDeclaredMethod(setMethodName, fieldType);

            ReflectionUtils.makeAccessible(setMethod);

            setMethod.invoke(bean, value);
        } catch (NoSuchFieldException e) {
            throw new BeansException("No such field '" + fieldName + "' in class " + beanClass.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new BeansException("No such setter method for field '" + fieldName + "' in class " + beanClass.getName(), e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("Failed to set value for field '" + fieldName + "' in class " + beanClass.getName(), e);
        }
    }

}
