package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.BeansException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

}
