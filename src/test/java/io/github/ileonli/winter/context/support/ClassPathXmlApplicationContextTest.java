package io.github.ileonli.winter.context.support;

import io.github.ileonli.winter.testclass.support.*;
import io.github.ileonli.winter.testclass.support.CircularTestClass1;
import io.github.ileonli.winter.testclass.support.CircularTestClass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void buildBeanFromXml() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:context/support/ClassPathXmlApplicationContextTest.xml");
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
                new ClassPathXmlApplicationContext("classpath:context/support/InitMethodTest.xml");
        TestClass1 testClass1 = (TestClass1) context.getBean("testClass1");
        assertEquals(11, testClass1.getA());
    }

    @Test
    public void destroyMethod() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/DestroyMethodTest.xml");
        TestClass1 testClass1 = (TestClass1) context.getBean("testClass1");
        assertEquals(10, testClass1.getA());

        context.close();
        assertEquals(11, testClass1.getA()); // TestClass1.destroyMethod -> a + 1
    }

    @Test
    public void beanFactoryAware() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/BeanFactoryAwareTest.xml");
        BeanFactoryAwareTestClass testClass = (BeanFactoryAwareTestClass) context.getBean("testClass");
        assertEquals(testClass.getBeanFactory(), context.getBeanFactory());
    }

    @Test
    public void applicationContextAware() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/ApplicationContextAwareTest.xml");
        ApplicationContextAwareTestClass testClass = (ApplicationContextAwareTestClass) context.getBean("testClass");
        assertEquals(testClass.getApplicationContext(), context);
    }

    @Test
    public void prototypeBean() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/PrototypeBeanTest.xml");
        Object t1 = context.getBean("testClass1");
        Object t2 = context.getBean("testClass1");
        assertEquals(t1, t2);

        Object t3 = context.getBean("testClass2");
        Object t4 = context.getBean("testClass2");
        assertNotEquals(t3, t4);
    }

    @Test
    public void factoryBean() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/FactoryBeanTest.xml");
        Object t1 = context.getBean("singleton");
        Object t2 = context.getBean("singleton");
        assertEquals(t1, t2);
        assertEquals("singleton", ((FactoryBeanSingletonTest) t1).getS());
        assertEquals("singleton", ((FactoryBeanSingletonTest) t2).getS());

        Object t3 = context.getBean("prototype");
        Object t4 = context.getBean("prototype");
        assertNotEquals(t3, t4);
        assertEquals("prototype", ((FactoryBeanPrototypeTest) t3).getS());
        assertEquals("prototype", ((FactoryBeanPrototypeTest) t4).getS());
    }

    @Test
    public void circularReference() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/CircularReference.xml");
        CircularTestClass1 testClass1 = (CircularTestClass1) context.getBean("testClass1");
        CircularTestClass2 testClass2 = (CircularTestClass2) context.getBean("testClass2");

        assertSame(testClass1.getCircularTestClass2(), testClass2);
        assertSame(testClass2.getCircularTestClass1(), testClass1);
    }

}