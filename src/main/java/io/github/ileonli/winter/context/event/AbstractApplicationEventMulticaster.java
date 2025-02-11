package io.github.ileonli.winter.context.event;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.BeanFactory;
import io.github.ileonli.winter.beans.factory.BeanFactoryAware;
import io.github.ileonli.winter.context.ApplicationEvent;
import io.github.ileonli.winter.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();

    private BeanFactory beanFactory;

    @Override
    @SuppressWarnings("unchecked")
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
