package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.Advisor;
import io.github.ileonli.winter.aop.AfterReturningAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

class AfterReturningAdviceAdapter implements AdvisorAdapter {

    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof AfterReturningAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        AfterReturningAdvice advice = (AfterReturningAdvice) advisor.getAdvice();
        return new AfterReturningAdviceInterceptor(advice);
    }

}
