package io.github.ileonli.winter.context.support;

import io.github.ileonli.winter.testclass.TestClass1;
import io.github.ileonli.winter.testclass.TestClass2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void test() {
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

}