package io.github.ileonli.winter.beans.factory.support;

import cn.hutool.core.bean.DynaBean;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValue;
import io.github.ileonli.winter.beans.PropertyValues;
import io.github.ileonli.winter.beans.factory.BeanFactoryAware;
import io.github.ileonli.winter.beans.factory.DisposableBean;
import io.github.ileonli.winter.beans.factory.InitializingBean;
import io.github.ileonli.winter.beans.factory.config.*;

import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        return doCreateBean(beanName, beanDefinition);
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            // 发生短路（shortcut），说明该 Bean 已经完成：创建实例、属性注入、初始化，Spring 只需要处理其初始化后的操作
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor processor) {
                Object result = processor.postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition);

            // 如果返回 false 会阻止 Spring 自动注入属性
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!continueWithPropertyPopulation) {
                return bean;
            }

            // 属性填充阶段
            applyBeanPostprocessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition); // 可修改 PropertyValues
            applyPropertyValues(beanName, bean, beanDefinition); // 真正填充属性的逻辑

            bean = initializeBean(beanName, bean, beanDefinition); // 初始化阶段，内部有（BeanPostProcess 逻辑）
        } catch (Exception e) {
            throw new BeansException("Instantiate bean error: " + e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor processor) {
                // 只要有一个 postProcessAfterInstantiation 阻止属性注入，直接返回
                if (!processor.postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    private void applyBeanPostprocessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor processor) {
                PropertyValues oldPropertyValues = beanDefinition.getPropertyValues();
                PropertyValues newPropertyValues = processor.postProcessPropertyValues(oldPropertyValues, bean, beanName);
                if (newPropertyValues != null) {
                    for (PropertyValue propertyValue : newPropertyValues.getPropertyValues()) {
                        oldPropertyValues.addPropertyValue(propertyValue);
                    }
                }
            }
        }
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

        // property 中使用的是 ref，注入其它 Bean
        if (value instanceof BeanReference(String beanName)) {
            value = getBean(beanName);
        }

        // 通过 setter 方法注入属性
        DynaBean dynaBean = DynaBean.create(bean);
        dynaBean.set(fieldName, value);
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

    protected Object initializeBean(String beanName, Object bean, BeanDefinition bd) {
        if (bean instanceof BeanFactoryAware bfa) {
            bfa.setBeanFactory(this);
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            invokeInitMethods(beanName, wrappedBean, bd);
        } catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
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

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (beanDefinition.isPrototype()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
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

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
