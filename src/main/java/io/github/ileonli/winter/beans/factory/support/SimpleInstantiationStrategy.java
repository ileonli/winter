package io.github.ileonli.winter.beans.factory.support;

import cn.hutool.core.util.ReflectUtil;
import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Constructor<?> ctor = beanClass.getDeclaredConstructor();
            ReflectUtil.setAccessible(ctor);
            return ctor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BeansException(beanClass.getName() + " has no default constructor: " + e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeansException(e);
        }
    }

}
