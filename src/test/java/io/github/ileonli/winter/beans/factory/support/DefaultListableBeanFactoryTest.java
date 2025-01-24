package io.github.ileonli.winter.beans.factory.support;

import io.github.ileonli.winter.beans.PropertyValue;
import io.github.ileonli.winter.beans.PropertyValues;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DefaultListableBeanFactoryTest {

    public static class InjectionBean {
    }

    public static class TestClass {

        private int a;
        private String b;
        private InjectionBean bean;

        public TestClass() {
        }

        public TestClass(int a, String b) {
            this.a = a;
            this.b = b;
        }

        public TestClass(InjectionBean bean) {
            this.bean = bean;
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

        public InjectionBean getBean() {
            return bean;
        }

        public void setBean(InjectionBean bean) {
            this.bean = bean;
        }

    }


    @Test
    public void singletonObject() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", new BeanDefinition(TestClass.class));

        Object testClass1 = beanFactory.getBean("testClass");
        assertInstanceOf(TestClass.class, testClass1);

        Object testClass2 = beanFactory.getBean("testClass");
        assertEquals(testClass1, testClass2);
    }

    @Test
    public void applyPropertyValues() {
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

    @Test
    public void applyPropertyValuesWithBeanReference() {
        PropertyValues pvs = new PropertyValues();
        pvs.addPropertyValue(new PropertyValue("bean", new BeanReference("bean")));

        BeanDefinition bd = new BeanDefinition(TestClass.class, pvs);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", bd);
        beanFactory.registerBeanDefinition("bean", new BeanDefinition(InjectionBean.class));

        TestClass testClass = (TestClass) beanFactory.getBean("testClass");
        assertEquals(beanFactory.getBean("bean"), testClass.getBean());
    }

}