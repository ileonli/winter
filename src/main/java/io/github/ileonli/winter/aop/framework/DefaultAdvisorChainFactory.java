package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.*;
import io.github.ileonli.winter.aop.framework.adapter.AdvisorAdapterRegistry;
import io.github.ileonli.winter.aop.framework.adapter.DefaultAdvisorAdapterRegistry;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultAdvisorChainFactory implements AdvisorChainFactory {

    private static final AdvisorAdapterRegistry registry = new DefaultAdvisorAdapterRegistry();

    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport advised, Method method, Class<?> targetClass) {
        Advisor[] advisors = advised.getAdvisors().toArray(new Advisor[0]);
        List<Object> interceptorList = new ArrayList<>(advisors.length);
        Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
        for (Advisor advisor : advisors) {
            if (advisor instanceof PointcutAdvisor pa) {
                Pointcut pointcut = pa.getPointcut();
                ClassFilter classFilter = pointcut.getClassFilter();
                if (classFilter.matches(actualClass)) {
                    MethodMatcher matcher = pointcut.getMethodMatcher();
                    if (matcher.matches(method, actualClass)) {
                        // 将各种 Advice 统一转为环绕通知（MethodInterceptor）
                        MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
                        Collections.addAll(interceptorList, interceptors);
                    }
                }
            }
        }
        return interceptorList;
    }

}
