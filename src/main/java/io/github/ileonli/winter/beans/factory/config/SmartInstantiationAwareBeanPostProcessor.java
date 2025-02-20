package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValues;

public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor {

    // 不可以返回 null 对象，必须返回 Bean
    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
