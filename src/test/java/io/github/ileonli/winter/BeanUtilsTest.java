package io.github.ileonli.winter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class BeanUtilsTest {

    @Test
    void instantiateClassWithNoArgs() throws NoSuchMethodException {
        Class<TestClass> c = TestClass.class;
        Constructor<TestClass> ctor = c.getDeclaredConstructor();

        TestClass c1 = BeanUtils.instantiateClass(ctor);
        assertNotNull(c1);
        assertEquals(0, c1.getA());
        assertNull(c1.getB());
    }

    @Test
    void instantiateClassWithArgs() throws NoSuchMethodException {
        Class<TestClass> c = TestClass.class;
        Constructor<TestClass> ctor = c.getDeclaredConstructor(int.class, String.class);

        TestClass c1 = BeanUtils.instantiateClass(ctor, 10, "test");
        assertNotNull(c1);
        assertEquals(10, c1.getA());
        assertEquals("test", c1.getB());
    }

    @Test
    void instantiateClassWithProtectedCtor() throws NoSuchMethodException {
        Class<TestClass> c = TestClass.class;
        Constructor<TestClass> ctor = c.getDeclaredConstructor(int.class, String.class, int.class);

        TestClass c1 = BeanUtils.instantiateClass(ctor, 10, "test", 20);
        assertNotNull(c1);
        assertEquals(30, c1.getA());
        assertEquals("test", c1.getB());
    }

    @Test
    void instantiateClassWithPrivateCtor() throws NoSuchMethodException {
        Class<TestClass> c = TestClass.class;
        Constructor<TestClass> ctor = c.getDeclaredConstructor(int.class, int.class, String.class);

        TestClass c1 = BeanUtils.instantiateClass(ctor, 10, 20, "test");
        assertNotNull(c1);
        assertEquals(30, c1.getA());
        assertEquals("test", c1.getB());
    }

}

class TestClass {

    private int a;
    private String b;

    public TestClass() {
    }

    public TestClass(int a, String b) {
        this.a = a;
        this.b = b;
    }

    protected TestClass(int a, String b, int c) {
        this.a = a + c;
        this.b = b;
    }


    private TestClass(int a, int b, String c) {
        this.a = a + b;
        this.b = c;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TestClass testClass = (TestClass) o;
        return a == testClass.a && Objects.equals(b, testClass.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "a=" + a +
                ", b='" + b + '\'' +
                '}';
    }

}
