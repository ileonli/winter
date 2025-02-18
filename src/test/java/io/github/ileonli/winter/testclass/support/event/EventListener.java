package io.github.ileonli.winter.testclass.support.event;

import io.github.ileonli.winter.context.ApplicationListener;
import io.github.ileonli.winter.testclass.TestEvent;

import java.util.ArrayList;

public class EventListener implements ApplicationListener<TestEvent> {

    @Override
    @SuppressWarnings("unchecked")
    public void onApplicationEvent(TestEvent event) {
        ArrayList<Integer> source = (ArrayList<Integer>) event.getSource();
        source.add(1);
    }

}
