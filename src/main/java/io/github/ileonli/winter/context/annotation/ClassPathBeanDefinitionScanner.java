package io.github.ileonli.winter.context.annotation;

import cn.hutool.core.util.StrUtil;
import io.github.ileonli.winter.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.support.BeanDefinitionRegistry;
import io.github.ileonli.winter.stereotype.Component;

import java.beans.Introspector;
import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            "io.github.ileonli.winter.beans.factory.annotation.internalAutowiredAnnotationProcessor";

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> components = findCandidateComponents(basePackage);
            for (BeanDefinition bd : components) {
                String scope = resolveBeanScope(bd);
                if (StrUtil.isNotEmpty(scope)) {
                    bd.setScope(scope);
                } else {
                    bd.setScope(BeanDefinition.SCOPE_SINGLETON);
                }
                String beanName = determineBeanName(bd);
                registry.registerBeanDefinition(beanName, bd);
            }
        }

        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return null;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = Introspector.decapitalize(beanClass.getSimpleName());
        }
        return value;
    }

}
