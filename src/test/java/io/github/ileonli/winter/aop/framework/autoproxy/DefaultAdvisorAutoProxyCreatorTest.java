package io.github.ileonli.winter.aop.framework.autoproxy;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.CustomService;
import io.github.ileonli.winter.testclass.CustomServiceBeforeAdvice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultAdvisorAutoProxyCreatorTest {

    @Test
    public void beanFactoryAutoProxyCreator() throws NoSuchMethodException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:DefaultAdvisorAutoProxyCreatorTest.xml");

        CustomService service = (CustomService) context.getBean("customServiceImp");
        assertTrue(service.getClass().getSimpleName().contains("$Proxy"));

        service.f();

        CustomServiceBeforeAdvice advice = (CustomServiceBeforeAdvice) context.getBean("beforeAdvice");
        assertEquals(advice.invokeMethod.getName(), service.getClass().getDeclaredMethod("f").getName());
    }

}