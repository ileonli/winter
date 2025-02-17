package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;

public class ProxyFactory {

    private final AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        Object target = advisedSupport.getTargetSource().getTarget();
        if (advisedSupport.isProxyTargetClass() ||
                !hasInterface(target.getClass())) {
            return new CglibAopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }

    private boolean hasInterface(Class<?> targetClass) {
        return targetClass.getInterfaces().length > 0;
    }

}
