package io.github.ileonli.winter.testclass;

import io.github.ileonli.winter.beans.factory.FactoryBean;

public class FactoryBeanPrototypeTest implements FactoryBean<FactoryBeanPrototypeTest> {

    private String s;

    @Override
    public FactoryBeanPrototypeTest getObject() throws Exception {
        FactoryBeanPrototypeTest prototype = new FactoryBeanPrototypeTest();
        prototype.s = "prototype";
        return prototype;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

}