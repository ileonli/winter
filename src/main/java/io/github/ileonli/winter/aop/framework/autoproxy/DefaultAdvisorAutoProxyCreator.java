package io.github.ileonli.winter.aop.framework.autoproxy;

import io.github.ileonli.winter.aop.*;
import io.github.ileonli.winter.aop.aspectj.AspectJExpressionPointcutAdvisor;
import io.github.ileonli.winter.aop.framework.ProxyFactory;
import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.BeanFactory;
import io.github.ileonli.winter.beans.factory.BeanFactoryAware;
import io.github.ileonli.winter.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import io.github.ileonli.winter.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DefaultAdvisorAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = new HashSet<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(bean)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    private Object wrapIfNecessary(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        if (isInfrastructureClass(beanClass)) {
            return bean;
        }

        try {
            Collection<AspectJExpressionPointcutAdvisor> advisors =
                    beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    TargetSource targetSource = new TargetSource(bean);

                    advisedSupport.setTargetSource(targetSource);
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (Exception e) {
            throw new BeansException("Error create proxy bean for: " + beanName, e);
        }

        return bean;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

}
