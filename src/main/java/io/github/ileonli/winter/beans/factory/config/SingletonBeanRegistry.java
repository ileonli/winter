package io.github.ileonli.winter.beans.factory.config;

public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

}
 