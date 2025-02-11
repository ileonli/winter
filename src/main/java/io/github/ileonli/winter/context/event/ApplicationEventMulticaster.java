package io.github.ileonli.winter.context.event;

import io.github.ileonli.winter.context.ApplicationEvent;
import io.github.ileonli.winter.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);

}
