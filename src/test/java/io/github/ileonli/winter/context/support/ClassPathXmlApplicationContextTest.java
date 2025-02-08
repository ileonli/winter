package io.github.ileonli.winter.context.support;

import io.github.ileonli.winter.testclass.TestClass1;
import io.github.ileonli.winter.testclass.TestClass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void buildBeanFromXml() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:ClassPathXmlApplicationContextTest.xml");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        assertEquals(2, beanDefinitionNames.length);

        TestClass1 testClass1 = (TestClass1) context.getBean("testClass1");
        TestClass2 testClass2 = (TestClass2) context.getBean("testClass2");

        assertEquals(10, testClass1.getA());
        assertEquals("string", testClass1.getS());
        assertEquals(testClass2, testClass1.getTc2());
    }

    @Test
    public void initMethod() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:InitMethodTest.xml");
        TestClass1 testClass1 = (TestClass1) context.getBean("testClass1");
        assertEquals(11, testClass1.getA());
    }

    @Test
    public void destroyMethod() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:DestroyMethodTest.xml");
        TestClass1 testClass1 = (TestClass1) context.getBean("testClass1");
        assertEquals(10, testClass1.getA());

        context.close();
        assertEquals(11, testClass1.getA()); // TestClass1.destroyMethod -> a + 1
    }

}