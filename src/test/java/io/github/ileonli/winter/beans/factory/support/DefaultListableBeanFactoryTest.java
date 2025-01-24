package io.github.ileonli.winter.beans.factory.support;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValue;
import io.github.ileonli.winter.beans.PropertyValues;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanFactoryPostProcessor;
import io.github.ileonli.winter.beans.factory.config.BeanPostProcessor;
import io.github.ileonli.winter.beans.factory.config.BeanReference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void beanFactoryPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", new BeanDefinition(TestClass.class));

        BeanFactoryPostProcessor postProcessor = cbf -> {
            BeanDefinition bean = cbf.getBeanDefinition("testClass");
            assertNotNull(bean);

            DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) cbf;
            dbf.registerBeanDefinition(
                    "injectionBean", new BeanDefinition(InjectionBean.class));
        };
        postProcessor.postProcessBeanFactory(beanFactory);

        TestClass testClass = (TestClass) beanFactory.getBean("testClass");
        assertNotNull(testClass);

        InjectionBean injectionBean = (InjectionBean) beanFactory.getBean("injectionBean");
        assertNotNull(injectionBean);
    }

    @Test
    public void beanPostProcessor() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("testClass", new BeanDefinition(TestClass.class));
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof TestClass tc) {
                    tc.setA(10);
                    tc.setB("test");
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof TestClass tc) {
                    assertEquals(10, tc.getA());
                    assertEquals("test", tc.getB());

                    tc.setA(20);
                    tc.setB("testtest");
                }
                return bean;
            }
        });

        TestClass testClass = (TestClass) beanFactory.getBean("testClass");
        assertEquals(20, testClass.getA());
        assertEquals("testtest", testClass.getB());
    }

}