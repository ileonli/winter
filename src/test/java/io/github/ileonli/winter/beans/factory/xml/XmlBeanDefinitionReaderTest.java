package io.github.ileonli.winter.beans.factory.xml;

import io.github.ileonli.winter.beans.factory.support.DefaultListableBeanFactory;
import io.github.ileonli.winter.core.io.ClassPathResource;
import io.github.ileonli.winter.testclass.TestClass1;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void test() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("XmlBeanDefinitionReaderTest.xml"));

        String[] beanDefinitionNames = factory.getBeanDefinitionNames();
        assertEquals(3, beanDefinitionNames.length);

        Arrays.sort(beanDefinitionNames);
        assertEquals("testClass1", beanDefinitionNames[0]);
        assertEquals("testClass2", beanDefinitionNames[1]);
        assertEquals("testClass3", beanDefinitionNames[2]);

        TestClass1 testClass3 = (TestClass1) factory.getBean("testClass3");
        assertEquals(10, testClass3.getA());
        assertEquals("test", testClass3.getS());
        assertEquals(factory.getBean("testClass2"), testClass3.getTc2());
    }

}