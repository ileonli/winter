package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
