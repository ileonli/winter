package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.MethodMatcher;
import io.github.ileonli.winter.aop.TargetSource;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        TargetSource targetSource = advisedSupport.getTargetSource();
        ClassLoader loader = targetSource.getTarget().getClass().getClassLoader();
        Class<?>[] interfaces = targetSource.getTargetClass();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodMatcher matcher = advisedSupport.getMethodMatcher();
        Object target = advisedSupport.getTargetSource().getTarget();
        if (matcher.matches(method, target.getClass())) {
            MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
            return methodInterceptor.invoke(
                    new ReflectiveMethodInvocation(target, method, args)
            );
        }
        return method.invoke(target, args);
    }

}
