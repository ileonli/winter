package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.BeansException;
import io.github.ileonli.winter.factory.BeanFactory;
import io.github.ileonli.winter.factory.config.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name);
    }

    protected Object doGetBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition bd = getBeanDefinition(name);
        return createBean(name, bd);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
