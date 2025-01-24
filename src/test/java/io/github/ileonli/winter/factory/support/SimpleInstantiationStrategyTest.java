package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.factory.config.BeanDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleInstantiationStrategyTest {

    public static class TestClass {

        public TestClass() {
        }

    }

    @Test
    void instantiate() {
        SimpleInstantiationStrategy instantiation = new SimpleInstantiationStrategy();
        TestClass test = (TestClass) instantiation.instantiate(new BeanDefinition(TestClass.class));
        assertNotNull(test);
    }

}