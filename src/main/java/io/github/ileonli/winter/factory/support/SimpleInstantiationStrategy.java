package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.BeansException;
import io.github.ileonli.winter.ReflectionUtils;
import io.github.ileonli.winter.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Constructor<?> ctor = beanClass.getDeclaredConstructor();
            ReflectionUtils.makeAccessible(ctor);
            return ctor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BeansException(beanClass.getName() + " has no default constructor: " + e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeansException(e);
        }
    }

}
