package io.github.ileonli.winter.testclass.aop.framework.autoproxy;

import java.util.Objects;

public class CircularWithProxyTestClass1 {

    private CircularWithProxyTestClass2 circularWithProxyTestClass2;

    public CircularWithProxyTestClass2 getCircularWithProxyTestClass2() {
        return circularWithProxyTestClass2;
    }

    public void setCircularWithProxyTestClass2(CircularWithProxyTestClass2 circularWithProxyTestClass2) {
        this.circularWithProxyTestClass2 = circularWithProxyTestClass2;
    }

}
