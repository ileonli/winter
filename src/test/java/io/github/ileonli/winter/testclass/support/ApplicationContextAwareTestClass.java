package io.github.ileonli.winter.testclass.support;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.context.ApplicationContext;
import io.github.ileonli.winter.context.ApplicationContextAware;

public class ApplicationContextAwareTestClass implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
