package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.Advisor;
import io.github.ileonli.winter.aop.BeforeAdvice;
import io.github.ileonli.winter.aop.MethodBeforeAdvice;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

class MethodBeforeAdviceAdapter implements AdvisorAdapter {

    @Override
    public boolean supportsAdvice(Advice advice) {
        return advice instanceof BeforeAdvice;
    }

    @Override
    public MethodInterceptor getInterceptor(Advisor advisor) {
        // 将 MethodBeforeAdvice 转为 环绕通知（MethodInterceptor）
        MethodBeforeAdvice advice = (MethodBeforeAdvice) advisor.getAdvice();
        return new MethodBeforeAdviceInterceptor(advice);
    }

}

