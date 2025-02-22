package io.github.ileonli.winter.aop.framework;


import io.github.ileonli.winter.aop.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

public interface AdvisorChainFactory {

    List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport advised, Method method, Class<?> targetClass);

}
