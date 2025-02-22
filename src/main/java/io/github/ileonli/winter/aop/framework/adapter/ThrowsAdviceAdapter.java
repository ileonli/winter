package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.Advisor;
import io.github.ileonli.winter.aop.ThrowsAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

class ThrowsAdviceAdapter implements AdvisorAdapter {

    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof ThrowsAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        ThrowsAdvice advice = (ThrowsAdvice) advisor.getAdvice();
        return new ThrowsAdviceInterceptor(advice);
    }

}