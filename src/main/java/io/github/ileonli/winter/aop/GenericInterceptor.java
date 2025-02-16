package io.github.ileonli.winter.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class GenericInterceptor implements MethodInterceptor {

    private BeforeAdvice beforeAdvice;
    private AfterAdvice afterAdvice;
    private AfterReturningAdvice afterReturningAdvice;
    private ThrowsAdvice throwsAdvice;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Object target = invocation.getThis();

        Object result;
        try {
            if (beforeAdvice != null) {
                beforeAdvice.before(method, arguments, target);
            }
            result = invocation.proceed();
        } catch (Throwable e) {
            if (throwsAdvice != null) {
                throwsAdvice.throwsHandle(e, method, arguments, target);
            }
            throw e;
        } finally {
            if (afterAdvice != null) {
                afterAdvice.after(method, arguments, target);
            }
        }
        if (afterReturningAdvice != null) {
            afterReturningAdvice.afterReturning(result, method, arguments, target);
        }
        return result;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }

    public void setAfterReturningAdvice(AfterReturningAdvice afterReturningAdvice) {
        this.afterReturningAdvice = afterReturningAdvice;
    }

    public void setThrowsAdvice(ThrowsAdvice throwsAdvice) {
        this.throwsAdvice = throwsAdvice;
    }

}
