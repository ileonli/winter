package io.github.ileonli.winter.beans.factory.xml;

import io.github.ileonli.winter.beans.PropertyValues;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanReference;
import io.github.ileonli.winter.beans.factory.support.DefaultListableBeanFactory;
import io.github.ileonli.winter.core.io.ClassPathResource;
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
        assertEquals(4, beanDefinitionNames.length);

        Arrays.sort(beanDefinitionNames);
        assertEquals("testClass1", beanDefinitionNames[0]);
        assertEquals("testClass2", beanDefinitionNames[1]);
        assertEquals("testClass3", beanDefinitionNames[2]);
        assertEquals("testClass4", beanDefinitionNames[3]);

        BeanDefinition bd1 = factory.getBeanDefinition("testClass3");
        PropertyValues propertyValues1 = bd1.getPropertyValues();
        assertEquals(3, propertyValues1.getPropertyValues().length);
        assertEquals("20", propertyValues1.getPropertyValue("a").getValue());
        assertEquals("test", propertyValues1.getPropertyValue("s").getValue());
        String refBeanName = ((BeanReference) propertyValues1.getPropertyValue("tc2").getValue()).beanName();
        assertEquals("testClass2", refBeanName);

        BeanDefinition bd2 = factory.getBeanDefinition("testClass4");
        PropertyValues propertyValues2 = bd2.getPropertyValues();
        assertEquals(1, propertyValues2.getPropertyValues().length);
        assertEquals("20", propertyValues2.getPropertyValue("a").getValue());
        assertEquals(null, propertyValues2.getPropertyValue("s"));
        assertEquals(null, propertyValues2.getPropertyValue("tc2"));

    }

}