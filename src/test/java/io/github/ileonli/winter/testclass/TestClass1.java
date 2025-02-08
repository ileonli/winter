package io.github.ileonli.winter.testclass;

import io.github.ileonli.winter.beans.factory.InitializingBean;

public class TestClass1 implements InitializingBean {

    private int a;
    private String s;
    private TestClass2 tc2;

    public TestClass1() {
    }

    public TestClass1(int a, String s, TestClass2 tc2) {
        this.a = a;
        this.s = s;
        this.tc2 = tc2;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public TestClass2 getTc2() {
        return tc2;
    }

    public void setTc2(TestClass2 tc2) {
        this.tc2 = tc2;
    }

    public void initMethod() {
        this.a += 1;
    }

    public void destroyMethod() {
        this.a += 1;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.a = 10;
    }

}
