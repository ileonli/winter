package io.github.ileonli.winter.aop.framework.adapter;

import io.github.ileonli.winter.aop.AfterAdvice;
import io.github.ileonli.winter.aop.ThrowsAdvice;
import io.github.ileonli.winter.beans.BeansException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理函数只能是下边四种之一的形式（只能有 1 个参数或者 4 个参数）：
 * public void afterThrowing(Exception ex)
 * public void afterThrowing(RemoteException)
 * public void afterThrowing(Method method, Object[] args, Object target, Exception ex)
 * public void afterThrowing(Method method, Object[] args, Object target, ServletException ex)
 */
public class ThrowsAdviceInterceptor implements MethodInterceptor, AfterAdvice {

    private static final String AFTER_THROWING = "afterThrowing";

    private final ThrowsAdvice advice;

    private final Map<Class<?>, Method> exceptionHandlerMap = new HashMap<>();

    public ThrowsAdviceInterceptor(ThrowsAdvice advice) {
        this.advice = advice;

        Method[] methods = advice.getClass().getMethods();
        for (Method method : methods) {
            if (!method.getName().equals(AFTER_THROWING)) {
                continue;
            }
            Class<?> throwableParam = null;

            int parameterCount = method.getParameterCount();
            if (parameterCount == 1) {
                // just a Throwable parameter
                throwableParam = method.getParameterTypes()[0];
                if (!Throwable.class.isAssignableFrom(throwableParam)) {
                    throw new BeansException("Invalid afterThrowing signature: " +
                            "single argument must be a Throwable subclass");
                }
            } else if (parameterCount == 4) {
                // Method, Object[], target, throwable
                Class<?>[] paramTypes = method.getParameterTypes();
                if (!Method.class.equals(paramTypes[0]) || !Object[].class.equals(paramTypes[1]) ||
                        Throwable.class.equals(paramTypes[2]) || !Throwable.class.isAssignableFrom(paramTypes[3])) {
                    throw new BeansException("Invalid afterThrowing signature: " +
                            "four arguments must be Method, Object[], target, throwable: " + method);
                }
                throwableParam = paramTypes[3];
            }

            if (throwableParam == null) {
                throw new BeansException("Unsupported afterThrowing signature: single throwable argument " +
                        "or four arguments Method, Object[], target, throwable expected: " + method);
            }

            // An exception handler to register...
            Method existingMethod = this.exceptionHandlerMap.put(throwableParam, method);
            if (existingMethod != null) {
                throw new BeansException("Only one afterThrowing method per specific Throwable subclass " +
                        "allowed: " + method + " / " + existingMethod);
            }
        }

        if (this.exceptionHandlerMap.isEmpty()) {
            throw new BeansException("At least one handler method must be found in class [" + advice.getClass() + "]");
        }
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            Method exceptionHandler = getExceptionHandler(e);
            if (exceptionHandler != null) {
                invokeHandlerMethod(invocation, e, exceptionHandler);
            }
            throw e;
        }
    }

    private Method getExceptionHandler(Throwable exception) {
        Class<?> exceptionClass = exception.getClass();
        Method handler = this.exceptionHandlerMap.get(exceptionClass);
        while (handler == null && exceptionClass != Throwable.class) {
            exceptionClass = exceptionClass.getSuperclass();
            handler = this.exceptionHandlerMap.get(exceptionClass);
        }
        return handler;
    }

    private void invokeHandlerMethod(MethodInvocation invocation, Throwable e, Method method) throws Throwable {
        Object[] handlerArgs;
        if (method.getParameterCount() == 1) {
            handlerArgs = new Object[]{e};
        } else {
            handlerArgs = new Object[] {invocation.getMethod(), invocation.getArguments(), invocation.getThis(), e};
        }

        try {
            method.invoke(this.advice, handlerArgs);
        } catch (InvocationTargetException ex) {
            // 抛出反射调用方法时，原方法抛出的异常
            throw ex.getTargetException();
        }
    }

}
