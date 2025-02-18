package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.factory.HierarchicalBeanFactory;
import io.github.ileonli.winter.utils.StringValueResolver;


public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    String resolveEmbeddedValue(String value);

}
