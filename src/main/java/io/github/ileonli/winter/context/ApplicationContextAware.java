package io.github.ileonli.winter.context;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}