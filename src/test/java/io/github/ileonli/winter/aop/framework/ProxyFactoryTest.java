package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.TargetSource;
import io.github.ileonli.winter.aop.aspectj.AspectJExpressionPointcutAdvisor;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClass1;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClassImp1;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProxyFactoryTest {

    @Test
    public void getProxy() {
        final int addNum = 20;

        AopProxyTestClass1 testClass1 = new AopProxyTestClassImp1();
        TargetSource targetSource = new TargetSource(testClass1);
        MethodInterceptor methodInterceptor = invocation -> {
            int result = (int) invocation.proceed();
            return (Object) (result + addNum);
        };

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(methodInterceptor);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(targetSource);
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setInterfaces(AopProxyTestClassImp1.class.getInterfaces());

        proxyFactory.setProxyTargetClass(false);
        AopProxyTestClass1 jdkProxy = (AopProxyTestClass1) proxyFactory.getProxy();
        assertTrue(jdkProxy.getClass().getName().contains("$Proxy"));

        proxyFactory.setProxyTargetClass(true);
        AopProxyTestClass1 cglib = (AopProxyTestClass1) proxyFactory.getProxy();
        assertTrue(cglib.getClass().getName().contains("CGLIB"));
    }

}