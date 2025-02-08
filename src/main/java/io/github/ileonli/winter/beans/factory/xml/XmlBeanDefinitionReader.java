package io.github.ileonli.winter.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.github.ileonli.winter.beans.BeansException;
import io.github.ileonli.winter.beans.PropertyValue;
import io.github.ileonli.winter.beans.factory.config.BeanDefinition;
import io.github.ileonli.winter.beans.factory.config.BeanReference;
import io.github.ileonli.winter.beans.factory.support.AbstractBeanDefinitionReader;
import io.github.ileonli.winter.beans.factory.support.BeanDefinitionRegistry;
import io.github.ileonli.winter.core.io.Resource;
import io.github.ileonli.winter.core.io.ResourceLoader;

import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";

    private final ObjectMapper objectMapper = new XmlMapper();

    protected XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException e) {
            throw new BeansException(e);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) {
        try {
            JsonNode tree = objectMapper.readTree(inputStream);
            JsonNode beans = tree.path(BEAN_ELEMENT);
            if (beans.isMissingNode()) {
                return;
            }

            if (beans.isArray()) {
                beans.forEach(this::parseBeanNode);
            } else {
                parseBeanNode(beans);
            }
        } catch (IOException e) {
            throw new BeansException(e);
        }
    }

    private void parseBeanNode(JsonNode node) {
        String id = node.path(ID_ATTRIBUTE).asText(null);
        String name = node.path(NAME_ATTRIBUTE).asText(null);
        String classPath = node.path(CLASS_ATTRIBUTE).asText(null);
        String propertyInitMethod = node.path(INIT_METHOD_ATTRIBUTE).asText(null);
        String propertyDestroyMethod = node.path(DESTROY_METHOD_ATTRIBUTE).asText(null);

        Class<?> clazz;
        try {
            clazz = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            throw new BeansException("Cannot find class [" + classPath + "]");
        }

        String beanName = Objects.requireNonNullElseGet(id, () -> {
            if (!StrUtil.isEmpty(name)) {
                return name;
            }
            return Introspector.decapitalize(clazz.getSimpleName());
        });

        BeanDefinitionRegistry registry = getRegistry();
        if (registry.containsBeanDefinition(beanName)) {
            throw new BeansException("Bean [" + beanName + "] already exists");
        }

        BeanDefinition bd = new BeanDefinition(clazz);
        bd.setInitMethodName(propertyInitMethod);
        bd.setDestroyMethodName(propertyDestroyMethod);

        node.withArray(PROPERTY_ELEMENT).forEach(property -> {
            String propertyName = property.path(NAME_ATTRIBUTE).asText(null);
            String propertyValue = property.path(VALUE_ATTRIBUTE).asText(null);
            String propertyRef = property.path(REF_ATTRIBUTE).asText(null);

            if (StrUtil.isEmpty(propertyName)) {
                throw new BeansException("The name attribute cannot be null or empty");
            }

            Object value = propertyValue;
            if (!StrUtil.isEmpty(propertyRef)) {
                value = new BeanReference(propertyRef);
            }

            bd.getPropertyValues().addPropertyValue(new PropertyValue(propertyName, value));
        });

        registry.registerBeanDefinition(beanName, bd);
    }

}
