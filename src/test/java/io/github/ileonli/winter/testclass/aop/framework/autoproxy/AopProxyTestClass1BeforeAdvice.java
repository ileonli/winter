package io.github.ileonli.winter.testclass.aop.framework.autoproxy;

import io.github.ileonli.winter.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class AopProxyTestClass1BeforeAdvice implements MethodBeforeAdvice {

    public Method invokeMethod;

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        invokeMethod = method;
    }

}
