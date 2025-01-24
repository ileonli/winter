package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.BeansException;
import io.github.ileonli.winter.factory.config.BeanDefinition;

public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition) throws BeansException;

}
