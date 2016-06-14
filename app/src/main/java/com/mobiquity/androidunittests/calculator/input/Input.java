package com.mobiquity.androidunittests.calculator.input;

public abstract class Input {

    private InputType type;
    protected String value;

    protected Input(String value, InputType type) {
        this.value = value;
        this.type = type;
    }

    public String getDisplayString() {
        return value;
    }

    public InputType getType() {
        return type;
    }

    public boolean valueEquals(Input input) {
        Class clazz = getClass();
        return clazz.isInstance(input);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + value;
    }
}
