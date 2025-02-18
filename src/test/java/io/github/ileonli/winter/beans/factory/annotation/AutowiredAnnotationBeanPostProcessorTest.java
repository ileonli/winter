package io.github.ileonli.winter.beans.factory.annotation;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.ValueAnnotationTestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutowiredAnnotationBeanPostProcessorTest {

    @Test
    public void valueAnnotation() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:ValueAnnotationTest.xml");

        ValueAnnotationTestClass bean = (ValueAnnotationTestClass) context.getBean("valueAnnotationTestClass");
        assertEquals(10, bean.getA());
    }

}