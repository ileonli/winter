package io.github.ileonli.winter.beans.factory;

public interface InitializingBean {

    void afterPropertiesSet() throws Exception;

}