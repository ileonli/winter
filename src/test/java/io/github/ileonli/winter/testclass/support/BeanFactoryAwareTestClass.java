package io.github.ileonli.winter.testclass.support;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.BeanFactory;
import io.github.ileonli.winter.beans.factory.BeanFactoryAware;

public class BeanFactoryAwareTestClass implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

}
