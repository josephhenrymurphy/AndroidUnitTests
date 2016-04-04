package com.mobiquity.androidunittests.input;

public class NumericInput extends Input {

    public NumericInput(int value) {
        super(value, InputType.NUMBER);
    }

    public int getValue() {
        return Integer.parseInt(value);
    }
}
