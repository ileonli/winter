package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
