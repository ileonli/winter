package io.github.ileonli.winter.context.support;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.ConfigurableListableBeanFactory;
import io.github.ileonli.winter.beans.factory.config.BeanFactoryPostProcessor;
import io.github.ileonli.winter.beans.factory.config.BeanPostProcessor;
import io.github.ileonli.winter.context.ApplicationEvent;
import io.github.ileonli.winter.context.ApplicationListener;
import io.github.ileonli.winter.context.ConfigurableApplicationContext;
import io.github.ileonli.winter.context.event.ApplicationEventMulticaster;
import io.github.ileonli.winter.context.event.ContextClosedEvent;
import io.github.ileonli.winter.context.event.ContextRefreshedEvent;
import io.github.ileonli.winter.context.event.SimpleApplicationEventMulticaster;
import io.github.ileonli.winter.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        prepareBeanFactory(beanFactory);

        invokeBeanFactoryPostProcessors(beanFactory);

        registerBeanPostProcessors(beanFactory);

        initApplicationEventMulticaster();

        registerListeners();

        beanFactory.preInstantiateSingletons();

        finishRefresh();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public void close() {
        this.doClose();
    }

    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(this::doClose);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    protected void doClose() {
        publishEvent(new ContextClosedEvent(this));

        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

}
