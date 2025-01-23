package io.github.ileonli.winter.factory.support;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructorResolverTest {

    public static class TestClass {
        public TestClass() {
        }

        public TestClass(int value) {
        }

        public TestClass(Integer value) {
        }

        public TestClass(String name, int age) {
        }
    }

    @Test
    void testNoArgConstructor() {
        ConstructorResolver resolver = new ConstructorResolver();
        Constructor<?>[] ctors = TestClass.class.getConstructors();

        Constructor<?> constructor = resolver.autowireConstructor(ctors, null);
        assertNotNull(constructor);
        assertEquals(0, constructor.getParameterCount());
    }

    @Test
    void testPrimitiveAndWrapperCompatibility() {
        ConstructorResolver resolver = new ConstructorResolver();
        Constructor<?>[] ctors = TestClass.class.getConstructors();

        Constructor<?> constructor = resolver.autowireConstructor(ctors, new Object[]{10});
        assertNotNull(constructor);
        assertEquals(1, constructor.getParameterCount());

        Class<?> parameterType = constructor.getParameterTypes()[0];
        assertTrue(Objects.equals(parameterType, Integer.class) || Objects.equals(parameterType, int.class));
    }

    @Test
    void testStringAndIntConstructor() {
        ConstructorResolver resolver = new ConstructorResolver();
        Constructor<?>[] ctors = TestClass.class.getConstructors();

        Constructor<?> constructor = resolver.autowireConstructor(ctors, new Object[]{"test", 30});
        assertNotNull(constructor);
        assertEquals(2, constructor.getParameterCount());
        assertEquals(String.class, constructor.getParameterTypes()[0]);
        assertEquals(int.class, constructor.getParameterTypes()[1]);
    }

    @Test
    void testNoMatchingConstructor() {
        ConstructorResolver resolver = new ConstructorResolver();
        Constructor<?>[] ctors = TestClass.class.getConstructors();

        Constructor<?> constructor = resolver.autowireConstructor(ctors, new Object[]{"test1", "test2"});
        assertNull(constructor);
    }

    @Test
    void testNullArguments() {
        ConstructorResolver resolver = new ConstructorResolver();
        Constructor<?>[] ctors = TestClass.class.getConstructors();

        Constructor<?> constructor = resolver.autowireConstructor(ctors, new Object[]{null});
        assertNotNull(constructor);
        assertEquals(1, constructor.getParameterCount());
        assertEquals(Integer.class, constructor.getParameterTypes()[0]);
    }

}
