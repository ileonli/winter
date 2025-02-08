package io.github.ileonli.winter.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValue;
import io.github.ileonli.winter.beans.PropertyValues;
import io.github.ileonli.winter.beans.factory.DisposableBean;
import io.github.ileonli.winter.beans.factory.InitializingBean;
import io.github.ileonli.winter.beans.factory.config.AutowireCapableBeanFactory;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanPostProcessor;
import io.github.ileonli.winter.beans.factory.config.BeanReference;

import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final InstantiationStrategy instantiationStrategy;

    public AbstractAutowireCapableBeanFactory() {
        this.instantiationStrategy = new SimpleInstantiationStrategy();
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition);
            applyPropertyValues(beanName, bean, beanDefinition);
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiate bean error: " + e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        addSingleton(beanName, bean);
        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition bd) {
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            invokeInitMethods(beanName, wrappedBean, bd);
        } catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    protected void applyPropertyValues(String beanName, Object existBean, BeanDefinition beanDefinition) {
        if (existBean == null || beanDefinition == null) {
            throw new IllegalArgumentException("Bean instance or BeanDefinition must not be null");
        }

        PropertyValues pvs = beanDefinition.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        for (PropertyValue pv : pvs.getPropertyValues()) {
            try {
                applyPropertyValue(existBean, pv);
            } catch (BeansException e) {
                throw new BeansException("Failed to apply property '" + pv.getName() + "' to bean '" + beanName + "'", e);
            }
        }
    }

    private void applyPropertyValue(Object bean, PropertyValue pv) {
        String fieldName = pv.getName();
        Object value = pv.getValue();

        if (value == null) {
            throw new BeansException("Property value for field '" + fieldName + "' is null");
        }

        if (value instanceof BeanReference(String beanName)) {
            value = getBean(beanName);
        }

        BeanUtil.setFieldValue(bean, fieldName, value);
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean ib) {
            ib.afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isEmpty(initMethodName)) {
            return;
        }

        Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
        if (initMethod == null) {
            throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
        }

        initMethod.invoke(bean);
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

}
