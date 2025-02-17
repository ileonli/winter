package io.github.ileonli.winter.testclass;

import io.github.ileonli.winter.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class CustomServiceBeforeAdvice implements MethodBeforeAdvice {

    public Method invokeMethod;

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        invokeMethod = method;
        method.invoke(target, args);
    }

}
