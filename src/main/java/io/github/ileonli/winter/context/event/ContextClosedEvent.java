package io.github.ileonli.winter.context.event;

import io.github.ileonli.winter.context.ApplicationContext;

public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }

}
