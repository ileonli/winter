package io.github.ileonli.winter.testclass.aop.framework.autoproxy;

import java.util.Objects;

public class CircularWithProxyTestClass2 {

    private CircularWithProxyTestClass1 circularWithProxyTestClass1;

    public CircularWithProxyTestClass1 getCircularWithProxyTestClass1() {
        return circularWithProxyTestClass1;
    }

    public void setCircularWithProxyTestClass1(CircularWithProxyTestClass1 circularWithProxyTestClass1) {
        this.circularWithProxyTestClass1 = circularWithProxyTestClass1;
    }

}
