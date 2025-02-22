package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.Advisor;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

public interface AdvisorAdapter {

	boolean supportsAdvice(Advice advice);

	MethodInterceptor getInterceptor(Advisor advisor);

}
