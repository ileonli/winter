package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.BeansException;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}