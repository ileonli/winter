package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanPostProcessor;
import io.github.ileonli.winter.beans.factory.config.ConfigurableBeanFactory;
import io.github.ileonli.winter.beans.factory.support.AutowireCapableBeanFactory;

public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
