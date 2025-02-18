package io.github.ileonli.winter.beans.factory;

import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValue;
import io.github.ileonli.winter.beans.PropertyValues;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanFactoryPostProcessor;
import io.github.ileonli.winter.core.io.DefaultResourceLoader;
import io.github.ileonli.winter.core.io.Resource;
import io.github.ileonli.winter.utils.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties properties = loadProperties();
        if (properties == null) {
            return;
        }

        processProperties(beanFactory, properties);

        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(valueResolver);
    }

    private Properties loadProperties() {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues pvs = beanDefinition.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }

        PropertyValue[] propertyValues = pvs.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            Object value = pv.getValue();
            if (value instanceof String v) {
                String resolved = resolvePlaceholder(v, properties);
                pvs.addPropertyValue(new PropertyValue(pv.getName(), resolved));
            }
        }
    }

    private String resolvePlaceholder(String v, Properties properties) {
        StringBuilder sb = new StringBuilder(v);
        int startIndex = v.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = v.indexOf(PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = v.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
            String propVal = properties.getProperty(propKey);
            sb.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
        }
        return sb.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        public String resolveStringValue(String strVal) throws BeansException {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }
    }

}
