package io.github.ileonli.winter.context.annotation;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.ComponentTestClass1;
import io.github.ileonli.winter.testclass.ComponentTestClass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackageScanTest {

    @Test
    public void scan() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:ComponentTestClass.xml");

        ComponentTestClass1 bean1 = (ComponentTestClass1) context.getBean("componentTestClass1");
        assertTrue(bean1 != null);

        ComponentTestClass2 bean2 = (ComponentTestClass2) context.getBean("componentTestClass2");
        assertTrue(bean2 != null);
    }

}
