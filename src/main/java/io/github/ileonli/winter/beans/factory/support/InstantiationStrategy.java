package io.github.ileonli.winter.beans.factory.support;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;

public interface InstantiationStrategy {

    Object instantiate(BeanDefinition beanDefinition) throws BeansException;

}
