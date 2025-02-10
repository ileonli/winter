package io.github.ileonli.winter.beans.factory;

public interface FactoryBean<T> {

    T getObject() throws Exception;

    default boolean isSingleton() {
        return true;
    }

}
