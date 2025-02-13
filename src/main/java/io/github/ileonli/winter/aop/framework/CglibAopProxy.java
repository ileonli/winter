package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.MethodMatcher;
import io.github.ileonli.winter.aop.TargetSource;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibAopProxy implements AopProxy {

    private final AdvisedSupport advisedSupport;

    public CglibAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        TargetSource targetSource = advisedSupport.getTargetSource();
        enhancer.setSuperclass(targetSource.getTarget().getClass());
        enhancer.setInterfaces(targetSource.getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        return enhancer.create();
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.getThis(), this.getArguments());
        }

    }

    private record DynamicAdvisedInterceptor(AdvisedSupport advised) implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Object target = advised.getTargetSource().getTarget();
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(target, method, objects, methodProxy);
            MethodMatcher matcher = advised.getMethodMatcher();
            if (matcher.matches(method, target.getClass())) {
                return advised.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }

    }

}
