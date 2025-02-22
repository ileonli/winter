package io.github.ileonli.winter.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {

    private final Object proxy;

    private final Object target;

    private final Method method;

    private final Object[] arguments;

    private final Class<?> targetClass;

    private final List<Object> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                      Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Object proceed() throws Throwable {
        // 每次都会创建一个新的 ReflectiveMethodInvocation 对象，因此不需要再次将 currentInterceptorIndex = -1
        if (currentInterceptorIndex == interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(target, arguments);
        }

        Object interceptor = interceptorsAndDynamicMethodMatchers.get(++currentInterceptorIndex);
        return ((MethodInterceptor) interceptor).invoke(this);
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }

}