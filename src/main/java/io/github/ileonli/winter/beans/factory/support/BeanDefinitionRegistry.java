package io.github.ileonli.winter.beans.factory.support;

import io.github.ileonli.winter.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
