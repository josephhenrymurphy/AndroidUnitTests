package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class NumericInputTest {

    private NumericInput numericInput;
    private static final int NUMERIC_INPUT_VALUE = 3;

    @Before
    public void setUp() {
        numericInput = new NumericInput(NUMERIC_INPUT_VALUE);
    }

    @Test
    public void testNumeric_InputType() {
        assertThat(numericInput.getType()).isEqualTo(InputType.NUMBER);
    }

    @Test
    public void testNumeric_Value() {
        assertThat(numericInput.getValue()).isEqualTo(3);
    }

    @Test
    public void testEquals_Symmetric() {
        NumericInput first = new NumericInput(1);
        NumericInput second = new NumericInput(1);

        assertThat(first).isEqualTo(first);
        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    public void testNotEquals() {
        NumericInput input = new NumericInput(3);
        assertThat(input).isNotEqualTo(null);
        assertThat(input).isNotEqualTo(new Object());
    }

}