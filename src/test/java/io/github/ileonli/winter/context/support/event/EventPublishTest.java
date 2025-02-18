package io.github.ileonli.winter.context.support.event;

import io.github.ileonli.winter.context.support.ClassPathXmlApplicationContext;
import io.github.ileonli.winter.testclass.support.event.TestEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventPublishTest {

    @Test
    public void publish() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:context/support/event/EventPublishTest.xml");

        ArrayList<Integer> list = new ArrayList<>();
        context.publishEvent(new TestEvent(list));

        // io.github.ileonli.winter.testclass.support.event.EventListener.onApplicationEvent will add 1
        assertEquals(1, list.size());
        assertEquals(1, list.getFirst());
    }

}
