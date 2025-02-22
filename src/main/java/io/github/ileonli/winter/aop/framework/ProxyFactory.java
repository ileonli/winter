package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;

public class ProxyFactory extends AdvisedSupport {

    public ProxyFactory() {
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (this.isProxyTargetClass() ||
                !hasInterface()) {
            return new CglibAopProxy(this);
        }
        return new JdkDynamicAopProxy(this);
    }

    private boolean hasInterface() {
        return getInterfaces().length > 0;
    }

}
