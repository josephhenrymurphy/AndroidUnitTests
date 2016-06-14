package com.mobiquity.androidunittests.calculator.input;

import com.mobiquity.androidunittests.calculator.CalculatorConstants;

public class NumericInput extends Input {
    public NumericInput(String value) {
        super(value, InputType.NUMBER);
    }

    public NumericInput(double value) {
        super(CalculatorConstants.DECIMAL_FORMAT.format(value), InputType.NUMBER);
    }

    public double getValue() {
        return Double.parseDouble(value);
    }

    @Override
    public boolean valueEquals(Input input) {
        return super.valueEquals(input) &&
                ((NumericInput) input).getValue() == getValue();
    }
}
