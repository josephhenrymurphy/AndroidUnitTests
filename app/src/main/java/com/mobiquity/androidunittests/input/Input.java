package com.mobiquity.androidunittests.input;

import java.security.InvalidParameterException;

public abstract class Input {

    private InputType type;
    protected String value;

    public Input(String value, InputType type) {
        this.value = value;
        this.type = type;
    }

    public Input(int value, InputType type) {
        if(type == InputType.OPERATOR) {
            throw new InvalidParameterException("An operator cannot be a number");
        }

        this.value = String.valueOf(value);
        this.type = type;
    }

    public InputType getType() {
        return type;
    }
}
