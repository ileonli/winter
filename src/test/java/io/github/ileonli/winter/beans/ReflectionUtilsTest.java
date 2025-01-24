package io.github.ileonli.winter.beans;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class ReflectionUtilsTest {

    public static class TestClass {

        private String privateField;

        private TestClass() {
        }

        private void privateMethod() {
        }

    }

    @Test
    void testMakeAccessibleConstructor() throws NoSuchMethodException {
        Constructor<TestClass> privateConstructor = TestClass.class.getDeclaredConstructor();
        assertFalse(privateConstructor.isAccessible());

        ReflectionUtils.makeAccessible(privateConstructor);

        assertTrue(privateConstructor.isAccessible());
    }

    @Test
    void testMakeAccessibleField() throws NoSuchFieldException {
        Field privateField = TestClass.class.getDeclaredField("privateField");
        assertFalse(privateField.isAccessible());

        ReflectionUtils.makeAccessible(privateField);

        assertTrue(privateField.isAccessible());
    }

    @Test
    void testMakeAccessibleMethod() throws NoSuchMethodException {
        Method privateMethod = TestClass.class.getDeclaredMethod("privateMethod");
        assertFalse(privateMethod.isAccessible());

        ReflectionUtils.makeAccessible(privateMethod);

        assertTrue(privateMethod.isAccessible());
    }

}
