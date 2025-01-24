package io.github.ileonli.winter.beans.factory.config;

import java.util.Objects;

public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BeanReference that = (BeanReference) o;
        return Objects.equals(beanName, that.beanName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(beanName);
    }

}
