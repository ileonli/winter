package io.github.ileonli.winter.context.event;

import io.github.ileonli.winter.context.ApplicationContext;

public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }

}