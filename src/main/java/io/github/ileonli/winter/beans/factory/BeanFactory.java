package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}
