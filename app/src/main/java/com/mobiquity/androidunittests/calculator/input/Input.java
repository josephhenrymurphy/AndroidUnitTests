package com.mobiquity.androidunittests.calculator.input;

import java.security.InvalidParameterException;

public abstract class Input {

    private InputType type;
    protected String value;

    public Input(String value, InputType type) {
        if(type == InputType.NUMBER) {
            throw new InvalidParameterException("The value for a number cannot be a string");
        }

        this.value = value;
        this.type = type;
    }

    public Input(int value, InputType type) {
        if(type != InputType.NUMBER) {
            throw new InvalidParameterException("Input type must be a number");
        }

        this.value = String.valueOf(value);
        this.type = type;
    }

    public String getDisplayString() {
        return value;
    }

    public InputType getType() {
        return type;
    }
}
