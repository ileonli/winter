package io.github.ileonli.winter.context;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
