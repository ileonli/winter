package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.Advisor;
import io.github.ileonli.winter.beans.BeansException;
import org.aopalliance.intercept.MethodInterceptor;

public interface AdvisorAdapterRegistry {

    MethodInterceptor[] getInterceptors(Advisor advisor) throws BeansException;

    void registerAdvisorAdapter(AdvisorAdapter adapter);

}
