package io.github.ileonli.winter.aop.aspectj;

import io.github.ileonli.winter.testclass.aop.aspectj.AspectJExpressionPointcutTestClass1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AspectJExpressionPointcutTest {

    @Test
    public void pointcutExpression() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
                "execution(* io.github.ileonli.winter.testclass.aop.aspectj.AspectJExpressionPointcutTestClass1.*(..))");
        Class<AspectJExpressionPointcutTestClass1> clazz = AspectJExpressionPointcutTestClass1.class;

        assertTrue(pointcut.matches(clazz));
        assertTrue(pointcut.matches(clazz.getMethod("f1"), clazz));
    }

}