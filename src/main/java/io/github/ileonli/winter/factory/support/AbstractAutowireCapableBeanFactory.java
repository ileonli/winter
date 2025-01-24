package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.BeansException;
import io.github.ileonli.winter.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private final InstantiationStrategy instantiationStrategy;

    public AbstractAutowireCapableBeanFactory() {
        this.instantiationStrategy = new SimpleInstantiationStrategy();
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object instantiate = instantiationStrategy.instantiate(beanDefinition);
        addSingleton(beanName, instantiate);
        return instantiate;
    }

}
