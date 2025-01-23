package io.github.ileonli.winter.factory.config;

public class BeanDefinition {

    private Class<?> beanClass;

    public BeanDefinition() {
    }

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                '}';
    }

}
