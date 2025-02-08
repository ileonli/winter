package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();

}
