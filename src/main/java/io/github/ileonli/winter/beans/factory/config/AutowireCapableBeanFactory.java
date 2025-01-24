package io.github.ileonli.winter.beans.factory.config;


import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException;

}
