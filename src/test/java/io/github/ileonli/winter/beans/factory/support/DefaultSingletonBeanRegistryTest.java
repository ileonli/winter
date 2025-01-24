package io.github.ileonli.winter.beans.factory.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultSingletonBeanRegistryTest {

    @Test
    void test() {
        DefaultSingletonBeanRegistry beanRegistry = new DefaultSingletonBeanRegistry();
        Object hello = new Object(), world = new Object();
        beanRegistry.registerSingleton("hello", hello);
        beanRegistry.registerSingleton("world", world);

        assertEquals(hello, beanRegistry.getSingleton("hello"));
        assertEquals(world, beanRegistry.getSingleton("world"));
    }

}