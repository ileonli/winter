package io.github.ileonli.winter.testclass.beans.factory.annotation.autowired;

import io.github.ileonli.winter.beans.factory.annotation.Autowired;
import io.github.ileonli.winter.beans.factory.annotation.Qualifier;
import io.github.ileonli.winter.stereotype.Component;

@Component
public class AutowiredAnnotationTestClass {

    @Autowired
    TestClass1 testClass;

    @Qualifier("tc3")
    @Autowired
    TestClass2 testClass2;

    public TestClass1 getTestClass() {
        return testClass;
    }

    public void setTestClass(TestClass1 testClass) {
        this.testClass = testClass;
    }

    public TestClass2 getTestClass2() {
        return testClass2;
    }

    public void setTestClass2(TestClass2 testClass2) {
        this.testClass2 = testClass2;
    }

}
