package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.BeansException;
import io.github.ileonli.winter.ReflectionUtils;
import io.github.ileonli.winter.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {

    private final ConstructorResolver constructorResolver = new ConstructorResolver();

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] ctors = beanClass.getDeclaredConstructors();

        Constructor<?> ctor = constructorResolver.autowireConstructor(ctors, args);
        if (ctor == null) {
            throw new BeansException("No autowire constructor found for " + beanClass);
        }

        ReflectionUtils.makeAccessible(ctor);

        try {
            return ctor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException(e);
        }
    }

}
