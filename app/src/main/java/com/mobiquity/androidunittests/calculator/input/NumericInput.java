package com.mobiquity.androidunittests.calculator.input;

import com.mobiquity.androidunittests.util.HashCodeBuilder;

import java.util.Objects;

public class NumericInput extends Input {

    public NumericInput(int value) {
        super(value, InputType.NUMBER);
    }

    public int getValue() {
        return Integer.parseInt(value);
    }

    @Override
    public boolean equals(Object object) {
        if(object == this) {
            return  true;
        } else if(object == null || !(object instanceof NumericInput)) {
            return false;
        } else {
            NumericInput numericInput = (NumericInput) object;
            return numericInput.getValue() == this.getValue() &&
                    numericInput.getType() == this.getType();
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getValue())
                .append(getType())
                .getHash();
    }

}
