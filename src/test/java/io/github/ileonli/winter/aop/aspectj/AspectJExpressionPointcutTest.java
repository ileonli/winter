package io.github.ileonli.winter.aop.aspectj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AspectJExpressionPointcutTest {

    @Test
    public void pointcutExpression() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(
                "execution(* io.github.ileonli.winter.testclass.AspectJExpressionPointcutTest.*(..))");
        Class<io.github.ileonli.winter.testclass.AspectJExpressionPointcutTest> clazz =
                io.github.ileonli.winter.testclass.AspectJExpressionPointcutTest.class;

        assertTrue(pointcut.matches(clazz));
        assertTrue(pointcut.matches(clazz.getMethod("f1"), clazz));
    }

}