package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.TargetSource;
import io.github.ileonli.winter.aop.aspectj.AspectJExpressionPointcutAdvisor;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClass1;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClassImp1;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CglibAopProxyTest {

    @Test
    public void getProxy() {
        int addNum = 10;

        AopProxyTestClass1 testClass1 = new AopProxyTestClassImp1();
        MethodInterceptor methodInterceptor = invocation -> {
            int result = (int) invocation.proceed();
            return (Object) (result + addNum);
        };

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(methodInterceptor);
        advisor.setExpression("execution(* io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClass1.*(..))");

        AdvisedSupport support = new AdvisedSupport();
        support.addAdvisor(advisor);
        support.setTargetSource(new TargetSource(testClass1));

        AopProxyTestClass1 proxy = (AopProxyTestClass1) new CglibAopProxy(support).getProxy();

        int parameter = 10;
        assertEquals(addNum + parameter, proxy.f(parameter));
    }

}