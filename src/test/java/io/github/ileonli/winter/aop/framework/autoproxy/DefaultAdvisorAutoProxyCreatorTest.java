package io.github.ileonli.winter.aop.framework.autoproxy;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.aop.framework.AopProxyTestClass1;
import io.github.ileonli.winter.testclass.aop.framework.autoproxy.AopProxyTestClass1BeforeAdvice;
import io.github.ileonli.winter.testclass.aop.framework.autoproxy.CircularWithProxyTestClass1;
import io.github.ileonli.winter.testclass.aop.framework.autoproxy.CircularWithProxyTestClass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultAdvisorAutoProxyCreatorTest {

    @Test
    public void beanFactoryAutoProxyCreator() throws NoSuchMethodException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:aop/framework/autoproxy/DefaultAdvisorAutoProxyCreatorTest.xml");

        AopProxyTestClass1 service = (AopProxyTestClass1) context.getBean("aopProxyTestClassImp1");
        assertTrue(service.getClass().getSimpleName().contains("$Proxy"));
        assertEquals(99, service.getA());

        service.f(0);

        AopProxyTestClass1BeforeAdvice advice = (AopProxyTestClass1BeforeAdvice) context.getBean("beforeAdvice");
        assertEquals(advice.invokeMethod.getName(), service.getClass().getDeclaredMethod("f", int.class).getName());
    }

    @Test
    public void circularReferenceWithProxy() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:aop/framework/autoproxy/CircularReferenceWithProxy.xml");
        CircularWithProxyTestClass1 testClass1 = (CircularWithProxyTestClass1) context.getBean("testClass1");
        CircularWithProxyTestClass2 testClass2 = (CircularWithProxyTestClass2) context.getBean("testClass2");

        assertSame(testClass1.getCircularWithProxyTestClass2(), testClass2);
        assertSame(testClass2.getCircularWithProxyTestClass1(), testClass1);
    }

}