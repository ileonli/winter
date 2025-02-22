package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.TargetSource;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class CglibAopProxy implements AopProxy {

    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        TargetSource targetSource = advised.getTargetSource();
        enhancer.setSuperclass(targetSource.getTargetClass());
        enhancer.setInterfaces(advised.getInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object proxy, Object target, Method method,
                                     Object[] arguments, Class<?> targetClass,
                                     List<Object> chain, MethodProxy methodProxy) {
            super(proxy, target, method, arguments, targetClass, chain);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return super.proceed();
        }

    }

    private record DynamicAdvisedInterceptor(AdvisedSupport advised) implements MethodInterceptor {

        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object target = advised.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();
            List<Object> chain = advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);

            if (chain != null && !chain.isEmpty()) {
                CglibMethodInvocation invocation = new CglibMethodInvocation(
                        proxy, target, method, args, targetClass, chain, methodProxy
                );
                return invocation.proceed();
            }
            return methodProxy.invoke(target, args);
        }

    }

}
