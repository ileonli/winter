package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.PropertyValue;
import io.github.ileonli.winter.PropertyValues;
import io.github.ileonli.winter.factory.config.BeanDefinition;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DefaultListableBeanFactoryTest {

    public static class TestClass {

        private int a;
        private String b;

        public TestClass() {
        }

        public TestClass(int a, String b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            TestClass testClass = (TestClass) o;
            return a == testClass.a && Objects.equals(b, testClass.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

    }

    @Test
    public void singletonObjectTest() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", new BeanDefinition(TestClass.class));

        Object testClass1 = beanFactory.getBean("testClass");
        assertInstanceOf(TestClass.class, testClass1);

        Object testClass2 = beanFactory.getBean("testClass");
        assertEquals(testClass1, testClass2);
    }

    @Test
    public void applyPropertyValuesTest() {
        PropertyValues pvs = new PropertyValues();
        pvs.addPropertyValue(new PropertyValue("a", 10));
        pvs.addPropertyValue(new PropertyValue("b", "test"));

        BeanDefinition bd = new BeanDefinition(TestClass.class, pvs);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", bd);

        TestClass testClass = (TestClass) beanFactory.getBean("testClass");
        assertEquals(10, testClass.getA());
        assertEquals("test", testClass.getB());
    }

}