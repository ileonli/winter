package io.github.ileonli.winter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertyValues {

    private final List<PropertyValue> pvs;

    public PropertyValues() {
        this.pvs = new ArrayList<>();
    }

    public void addPropertyValue(PropertyValue pv) {
        Objects.requireNonNull(pv);

        int size = pvs.size();
        for (int i = 0; i < size; i++) {
            PropertyValue propertyValue = pvs.get(i);
            if (Objects.equals(propertyValue.getName(), pv.getName())) {
                pvs.set(i, pv);
                return;
            }
        }
        pvs.add(pv);
    }

    public PropertyValue getPropertyValue(String name) {
        for (PropertyValue pv : pvs) {
            if (Objects.equals(pv.getName(), name)) {
                return pv;
            }
        }
        return null;
    }

    public PropertyValue[] getPropertyValues() {
        return pvs.toArray(new PropertyValue[0]);
    }

    public boolean isEmpty() {
        return pvs.isEmpty();
    }

}
