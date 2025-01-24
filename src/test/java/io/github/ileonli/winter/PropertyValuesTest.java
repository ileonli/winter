package io.github.ileonli.winter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PropertyValuesTest {

    @Test
    void testAddPropertyValue() {
        PropertyValues propertyValues = new PropertyValues();

        PropertyValue pv1 = new PropertyValue("name", "John Doe");
        propertyValues.addPropertyValue(pv1);

        PropertyValue retrievedPv = propertyValues.getPropertyValue("name");
        assertNotNull(retrievedPv);
        assertEquals("John Doe", retrievedPv.getValue());

        PropertyValue pv2 = new PropertyValue("name", "Jane Doe");
        propertyValues.addPropertyValue(pv2);

        retrievedPv = propertyValues.getPropertyValue("name");
        assertNotNull(retrievedPv);
        assertEquals("Jane Doe", retrievedPv.getValue());
    }

    @Test
    void testGetPropertyValue() {
        PropertyValues propertyValues = new PropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("name", "John Doe"));
        propertyValues.addPropertyValue(new PropertyValue("age", 30));

        PropertyValue namePv = propertyValues.getPropertyValue("name");
        assertNotNull(namePv);
        assertEquals("John Doe", namePv.getValue());

        PropertyValue agePv = propertyValues.getPropertyValue("age");
        assertNotNull(agePv);
        assertEquals(30, agePv.getValue());

        PropertyValue nonExistentPv = propertyValues.getPropertyValue("nonExistent");
        assertNull(nonExistentPv);
    }

    @Test
    void testGetPropertyValues() {
        PropertyValues propertyValues = new PropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("name", "John Doe"));
        propertyValues.addPropertyValue(new PropertyValue("age", 30));
        propertyValues.addPropertyValue(new PropertyValue("city", "New York"));

        PropertyValue[] pvs = propertyValues.getPropertyValues();

        assertEquals(3, pvs.length);

        assertEquals("name", pvs[0].getName());
        assertEquals("John Doe", pvs[0].getValue());
        assertEquals("age", pvs[1].getName());
        assertEquals(30, pvs[1].getValue());
        assertEquals("city", pvs[2].getName());
        assertEquals("New York", pvs[2].getValue());
    }

    @Test
    void testAddPropertyValueWithNull() {
        PropertyValues propertyValues = new PropertyValues();

        assertThrows(NullPointerException.class, () -> {
            propertyValues.addPropertyValue(null);
        });
    }

    @Test
    void testIsEmpty() {
        PropertyValues propertyValues = new PropertyValues();
        assertTrue(propertyValues.isEmpty());

        propertyValues.addPropertyValue(new PropertyValue("name", "John Doe"));
        assertFalse(propertyValues.isEmpty());
    }

}