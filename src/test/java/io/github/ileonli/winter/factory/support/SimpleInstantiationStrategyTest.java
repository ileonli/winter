package io.github.ileonli.winter.factory.support;

import io.github.ileonli.winter.factory.config.BeanDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleInstantiationStrategyTest {

    public static class TestClass {

        int a;
        String b;

        public TestClass() {
        }

        public TestClass(int a) {
            this.a = a;
        }

        public TestClass(String b) {
            this.b = b;
        }

        public TestClass(int a, String b) {
            this.a = a;
            this.b = b;
        }

    }

    private BeanDefinition bd;
    private SimpleInstantiationStrategy instantiationStrategy;

    @BeforeEach
    void setUp() {
        bd = new BeanDefinition(TestClass.class);
        instantiationStrategy = new SimpleInstantiationStrategy();
    }

    @Test
    void instantiateWithDefaultConstructor() {
        TestClass testClass = (TestClass) instantiationStrategy.instantiate(bd, "testClass", new Object[0]);
        assertNotNull(testClass);
    }

    @Test
    void instantiateWithStringConstructor() {
        TestClass testClass = (TestClass) instantiationStrategy.instantiate(bd, "testClass", new Object[]{"test"});
        assertEquals("test", testClass.b);
    }

    @Test
    void instantiateWithIntAndStringConstructor() {
        TestClass testClass = (TestClass) instantiationStrategy.instantiate(bd, "testClass", new Object[]{10, "test"});
        assertEquals(10, testClass.a);
        assertEquals("test", testClass.b);
    }

}