package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.factory.config.BeanDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultListableBeanFactoryTest {

    public static class TestClass {
    }

    @Test
    public void test() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", new BeanDefinition(TestClass.class));

        Object testClass1 = beanFactory.getBean("testClass");
        assertInstanceOf(TestClass.class, testClass1);

        Object testClass2 = beanFactory.getBean("testClass");
        assertEquals(testClass1, testClass2);
    }
}