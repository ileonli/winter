package io.github.ileonli.winter.beans.factory.annotation;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.beans.factory.annotation.autowired.AutowiredAnnotationTestClass;
import io.github.ileonli.winter.testclass.beans.factory.annotation.autowired.TestClass2;
import io.github.ileonli.winter.testclass.beans.factory.annotation.value.ValueAnnotationTestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutowiredAnnotationBeanPostProcessorTest {

    @Test
    public void valueAnnotation() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:beans/factory/annotation/ValueAnnotationTest.xml");

        ValueAnnotationTestClass bean = (ValueAnnotationTestClass) context.getBean("valueAnnotationTestClass");
        assertEquals(10, bean.getA());
    }

    @Test
    public void autowiredAnnotation() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:beans/factory/annotation/AutowiredAnnotationTest.xml");

        AutowiredAnnotationTestClass bean =
                (AutowiredAnnotationTestClass) context.getBean("autowiredAnnotationTestClass");
        assertEquals(bean.getTestClass(), context.getBean("testClass1"));

        assertEquals(3, context.getBeansOfType(TestClass2.class).size());

        TestClass2 testClass2 = bean.getTestClass2();
        assertEquals(testClass2, context.getBean("tc3"));
    }

}