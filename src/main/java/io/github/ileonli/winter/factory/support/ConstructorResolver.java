package io.github.ileonli.winter.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class ConstructorResolver {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.put(int.class, Integer.class);
        PRIMITIVE_WRAPPER_MAP.put(long.class, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(double.class, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(float.class, Float.class);
        PRIMITIVE_WRAPPER_MAP.put(boolean.class, Boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(short.class, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(byte.class, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(char.class, Character.class);
    }

    public Constructor<?> autowireConstructor(Constructor<?>[] ctors, Object[] args) {
        if (ctors == null || ctors.length == 0) {
            return null;
        }

        if (args == null || args.length == 0) {
            for (Constructor<?> constructor : ctors) {
                if (constructor.getParameterCount() == 0) {
                    return constructor;
                }
            }
            return null;
        }

        for (Constructor<?> constructor : ctors) {
            if (isConstructorMatching(constructor, args)) {
                return constructor;
            }
        }

        return null;
    }


    private boolean isConstructorMatching(Constructor<?> constructor, Object[] args) {
        Parameter[] parameters = constructor.getParameters();
        if (parameters.length != args.length) {
            return false;
        }

        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameters[i].getType();
            Object arg = args[i];

            if (arg == null) {
                continue;
            }

            if (!isTypeCompatible(parameterType, arg.getClass())) {
                return false;
            }
        }

        return true;
    }

    private boolean isTypeCompatible(Class<?> targetType, Class<?> argType) {
        if (targetType.isAssignableFrom(argType)) {
            return true;
        }

        if (targetType.isPrimitive()) {
            Class<?> wrapperType = PRIMITIVE_WRAPPER_MAP.get(targetType);
            return wrapperType != null && wrapperType.isAssignableFrom(argType);
        } else if (argType.isPrimitive()) {
            Class<?> wrapperType = PRIMITIVE_WRAPPER_MAP.get(argType);
            return wrapperType != null && targetType.isAssignableFrom(wrapperType);
        }

        return false;
    }

}