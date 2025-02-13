package io.github.ileonli.winter.aop.framework;

import io.github.ileonli.winter.aop.AdvisedSupport;
import io.github.ileonli.winter.aop.TargetSource;
import io.github.ileonli.winter.aop.aspectj.AspectJExpressionPointcut;
import io.github.ileonli.winter.testclass.CustomService;
import io.github.ileonli.winter.testclass.CustomServiceImp;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CglibAopProxyTest {

    @Test
    public void getProxy() {
        CustomService service = new CustomServiceImp();
        TargetSource targetSource = new TargetSource(service);
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                int result = (int) invocation.proceed();
                return result + 10;
            }
        };
        AspectJExpressionPointcut pointcut =
                new AspectJExpressionPointcut("execution(* io.github.ileonli.winter.testclass.CustomService.*(..))");

        AdvisedSupport support = new AdvisedSupport(targetSource, methodInterceptor, pointcut);
        CustomService proxy = (CustomService) new CglibAopProxy(support).getProxy();

        assertEquals(20, proxy.f());
    }

}