package io.github.ileonli.winter.context;

import io.github.ileonli.winter.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh() throws BeansException;

    void close();

    void registerShutdownHook();

}
