package io.github.ileonli.winter.factory;

import io.github.ileonli.winter.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}
