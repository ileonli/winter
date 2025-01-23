package io.github.ileonli.winter.factory.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeanDefinitionTest {

    public static class TestClass {

        public TestClass() {
        }

        public TestClass(int a) {
        }

    }

    @Test
    public void beanDefinitionTest() {
        BeanDefinition bd = new BeanDefinition(TestClass.class);
        assertEquals(TestClass.class, bd.getBeanClass());
    }

}