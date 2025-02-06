package io.github.ileonli.winter.beans.factory.support;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.core.io.Resource;
import io.github.ileonli.winter.core.io.ResourceLoader;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;

}