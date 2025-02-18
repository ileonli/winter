package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;

}
