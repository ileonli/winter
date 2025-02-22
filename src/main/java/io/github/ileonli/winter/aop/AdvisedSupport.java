package io.github.ileonli.winter.aop;

import io.github.ileonli.winter.aop.framework.AdvisorChainFactory;
import io.github.ileonli.winter.aop.framework.DefaultAdvisorChainFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvisedSupport {

    private TargetSource targetSource;

    private boolean proxyTargetClass = false;

    private final transient Map<Integer, List<Object>> methodCache = new HashMap<>();

    private final List<Class<?>> interfaces = new ArrayList<>();

    private final List<Advisor> advisors = new ArrayList<>();

    private final AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    public AdvisedSupport() {
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    public void addInterface(Class<?> ifc) {
        Assert.notNull(ifc, "Interface must not be null");
        if (!ifc.isInterface()) {
            throw new IllegalArgumentException("[" + ifc.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(ifc)) {
            this.interfaces.add(ifc);
        }
    }

    public void setInterfaces(Class<?>... interfaces) {
        this.interfaces.clear();
        for (Class<?> ifc : interfaces) {
            addInterface(ifc);
        }
    }

    public Class<?>[] getInterfaces() {
        return this.interfaces.toArray(new Class<?>[0]);
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        Integer cacheKey = method.hashCode();
        List<Object> cached = this.methodCache.get(cacheKey);
        if (cached == null) {
            cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
                    this, method, targetClass
            );
            this.methodCache.put(cacheKey, cached);
        }
        return cached;
    }

}