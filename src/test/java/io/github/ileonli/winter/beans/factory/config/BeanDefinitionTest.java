package io.github.ileonli.winter.beans.factory.config;

import io.github.ileonli.winter.beans.PropertyValues;
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

    @Test
    public void beanDefinitionWithPropertyValuesTest() {
        PropertyValues pvs = new PropertyValues();

        BeanDefinition bd = new BeanDefinition(TestClass.class, pvs);
        assertEquals(TestClass.class, bd.getBeanClass());
        assertEquals(PropertyValues.class, pvs.getClass());
    }

}