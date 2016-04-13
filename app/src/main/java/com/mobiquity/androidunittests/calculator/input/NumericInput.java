package com.mobiquity.androidunittests.calculator.input;

public class NumericInput extends Input {

    public NumericInput(int value) {
        super(value, InputType.NUMBER);
    }

    public int getValue() {
        return Integer.parseInt(value);
    }

    @Override
    public boolean valueEquals(Input input) {
        return super.valueEquals(input) &&
                ((NumericInput) input).getValue() == getValue();
    }
}
