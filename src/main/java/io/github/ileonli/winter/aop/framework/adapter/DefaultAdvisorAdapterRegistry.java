package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.Advisor;
import io.github.ileonli.winter.beans.BeansException;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.ArrayList;
import java.util.List;

public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry {

    private final List<AdvisorAdapter> adapters = new ArrayList<>(3);

    public DefaultAdvisorAdapterRegistry() {
        // TODO: add other adapters
        registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
        registerAdvisorAdapter(new AfterReturningAdviceAdapter());
    }

    @Override
    public MethodInterceptor[] getInterceptors(Advisor advisor) throws BeansException {
        /// 此处返回数组，是因为一个类可以同时实现多个不同类型的 Advice，如下边代码所示：
        ///  static class TestAdvice implements MethodBeforeAdvice, ThrowsAdvice {
        ///      @Override
        ///      public void before(Method method, Object[] args, Object target) throws Throwable {
        ///      }
        ///
        ///      public void afterThrowing(Exception ex) {
        ///      }
        ///  }

        List<MethodInterceptor> interceptors = new ArrayList<>(3);
        Advice advice = advisor.getAdvice();
        if (advice instanceof MethodInterceptor methodInterceptor) {
            interceptors.add(methodInterceptor);
        }
        for (AdvisorAdapter adapter : this.adapters) {
            if (adapter.supportsAdvice(advice)) {
                interceptors.add(adapter.getInterceptor(advisor));
            }
        }
        if (interceptors.isEmpty()) {
            throw new BeansException("Advice object [" + advice + "] is neither a supported subinterface of " +
                    "[org.aopalliance.aop.Advice] nor an [org.springframework.aop.Advisor]");
        }
        return interceptors.toArray(new MethodInterceptor[0]);
    }

    @Override
    public void registerAdvisorAdapter(AdvisorAdapter adapter) {
        this.adapters.add(adapter);
    }

}