package io.github.ileonli.winter.context.event;

import io.github.ileonli.winter.context.ApplicationContext;
import io.github.ileonli.winter.context.ApplicationEvent;

public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}
