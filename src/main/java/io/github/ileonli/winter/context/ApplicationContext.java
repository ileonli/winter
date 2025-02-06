package io.github.ileonli.winter.context;

import io.github.ileonli.winter.beans.factory.HierarchicalBeanFactory;
import io.github.ileonli.winter.beans.factory.ListableBeanFactory;
import io.github.ileonli.winter.core.io.ResourceLoader;

public interface ApplicationContext extends ResourceLoader, ListableBeanFactory, HierarchicalBeanFactory {
}
