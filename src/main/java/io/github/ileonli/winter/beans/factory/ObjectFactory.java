package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.beans.BeansException;

public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}