package io.github.ileonli.winter.aop;

public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?> getTargetClass() {
        return this.target.getClass();
    }

    public Object getTarget() {
        return this.target;
    }

}