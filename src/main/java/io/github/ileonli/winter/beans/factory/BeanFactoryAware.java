package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.beans.BeansException;

public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}