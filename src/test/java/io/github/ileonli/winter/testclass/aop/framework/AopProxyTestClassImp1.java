package io.github.ileonli.winter.testclass.aop.framework;

public class AopProxyTestClassImp1 implements AopProxyTestClass1 {

    private int a;

    @Override
    public int f(int a) {
        return a;
    }

    public int getA() {
        return this.a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "AopProxyTestClassImp1{" +
                "a=" + a +
                '}';
    }

}