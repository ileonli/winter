package io.github.ileonli.winter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    @SuppressWarnings("deprecation")
    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
                && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

}
