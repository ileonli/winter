package io.github.ileonli.winter.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    @SuppressWarnings("deprecation")
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    @SuppressWarnings("deprecation")
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

}
