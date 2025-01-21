package io.github.ileonli.winter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionUtilsTest {

    @Test
    @SuppressWarnings("deprecation")
    void makeAccessible() throws NoSuchMethodException {
        Constructor<?> ctor = NonPublicConstructorClass.class.getDeclaredConstructor();

        assertFalse(Modifier.isPublic(ctor.getModifiers()));
        assertFalse(Modifier.isPublic(ctor.getDeclaringClass().getModifiers()));
        assertFalse(ctor.isAccessible());

        ReflectionUtils.makeAccessible(ctor);
        assertTrue(ctor.isAccessible());
    }

}

class NonPublicConstructorClass {
    private NonPublicConstructorClass() {
    }
}
