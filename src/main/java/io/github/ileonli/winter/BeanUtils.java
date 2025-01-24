package io.github.ileonli.winter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class BeanUtils {

    private BeanUtils() {
    }

    private static final Map<Class<?>, Object> DEFAULT_TYPE_VALUES = Map.of(
            boolean.class, false,
            byte.class, (byte) 0,
            short.class, (short) 0,
            int.class, 0,
            long.class, 0L,
            float.class, 0F,
            double.class, 0D,
            char.class, '\0'
    );

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeansException {
        Objects.requireNonNull(ctor);

        try {
            ReflectionUtils.makeAccessible(ctor);
            int parameterCount = ctor.getParameterCount();
            if (args.length > parameterCount) {
                throw new BeansException("Can't specify more arguments than constructor parameters");
            }
            if (parameterCount == 0) {
                return ctor.newInstance();
            }
            Class<?>[] parameterTypes = ctor.getParameterTypes();
            Object[] argsWithDefaultValues = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    Class<?> parameterType = parameterTypes[i];
                    argsWithDefaultValues[i] = (parameterType.isPrimitive() ? DEFAULT_TYPE_VALUES.get(parameterType) : null);
                } else {
                    argsWithDefaultValues[i] = args[i];
                }
            }
            return ctor.newInstance(argsWithDefaultValues);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeansException(e);
        }
    }

    public static String fieldNameToSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void injectFieldValue(Class<?> clazz, Object target, String fieldName, Object value)
            throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field field = clazz.getDeclaredField(fieldName);
        Class<?> fieldType = field.getType();

        String setMethodName = BeanUtils.fieldNameToSetMethodName(fieldName);
        Method setMethod = clazz.getDeclaredMethod(setMethodName, fieldType);

        ReflectionUtils.makeAccessible(setMethod);

        setMethod.invoke(target, value);
    }

}
