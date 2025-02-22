package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.TargetSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        TargetSource targetSource = advised.getTargetSource();
        ClassLoader loader = targetSource.getTargetClass().getClassLoader();
        Class<?>[] interfaces = advised.getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = advised.getTargetSource().getTarget();
        List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, target.getClass());

        if (chain != null && !chain.isEmpty()) {
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(
                    proxy, target, method, args, target.getClass(), chain
            );
            return invocation.proceed();
        }
        return method.invoke(target, args);
    }

}
