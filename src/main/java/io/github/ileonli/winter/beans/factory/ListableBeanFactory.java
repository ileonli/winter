package io.github.ileonli.winter.beans.factory;


import io.github.ileonli.winter.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();

}
