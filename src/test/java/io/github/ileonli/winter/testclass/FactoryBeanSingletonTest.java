package io.github.ileonli.winter.testclass;

import io.github.ileonli.winter.beans.factory.FactoryBean;

public class FactoryBeanSingletonTest implements FactoryBean<FactoryBeanSingletonTest> {

    private String s;

    @Override
    public FactoryBeanSingletonTest getObject() throws Exception {
        FactoryBeanSingletonTest singleton = new FactoryBeanSingletonTest();
        singleton.s = "singleton";
        return singleton;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

}