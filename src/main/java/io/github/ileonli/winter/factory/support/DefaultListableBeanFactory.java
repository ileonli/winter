package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.BeansException;
import io.github.ileonli.winter.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition bd = beanDefinitionMap.get(beanName);
        if (bd == null) {
            throw new BeansException("No bean named '" + beanName + "' available");
        }
        return bd;
    }

}
