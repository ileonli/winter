package io.github.ileonli.winter.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}