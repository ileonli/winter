package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.PropertyPlaceholderConfigurerTestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyPlaceholderConfigurerTest {

    @Test
    public void propertyPlaceholder() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:beans/factory/PropertyPlaceholderConfigurerTest.xml");

        PropertyPlaceholderConfigurerTestClass bean =
                (PropertyPlaceholderConfigurerTestClass) context.getBean("bean");

        assertEquals(10, bean.getA());
        assertEquals("test", bean.getS());
    }

}