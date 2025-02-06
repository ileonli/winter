package io.github.ileonli.winter.context.support;

import io.github.ileonli.winter.beans.BeansException;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private final String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    protected String[] getConfigLocations() {
        return this.configLocations;
    }

}
