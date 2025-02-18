package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.TargetSource;
import io.github.ileonli.winter.aop.aspectj.AspectJExpressionPointcut;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClass1;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClassImp1;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdkDynamicAopProxyTest {

    @Test
    public void getProxy() {
        int addNum = 20;

        AopProxyTestClass1 testClass1 = new AopProxyTestClassImp1();
        TargetSource targetSource = new TargetSource(testClass1);
        MethodInterceptor methodInterceptor = invocation -> {
            int result = (int) invocation.proceed();
            return (Object) (result + addNum);
        };
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
                "execution(* io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClass1.*(..))");

        AdvisedSupport support = new AdvisedSupport(targetSource, methodInterceptor, pointcut);
        AopProxyTestClass1 proxy = (AopProxyTestClass1) new JdkDynamicAopProxy(support).getProxy();

        int parameter = 10;
        assertEquals(addNum + parameter, proxy.f(parameter));
    }

}