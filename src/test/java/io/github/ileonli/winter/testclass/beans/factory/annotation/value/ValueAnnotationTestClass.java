package io.github.ileonli.winter.testclass.beans.factory.annotation.value;

import io.github.ileonli.winter.beans.factory.annotation.Value;
import io.github.ileonli.winter.stereotype.Component;

@Component
public class ValueAnnotationTestClass {

    @Value("${a}")
    private int a;

    public void setA(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

}
