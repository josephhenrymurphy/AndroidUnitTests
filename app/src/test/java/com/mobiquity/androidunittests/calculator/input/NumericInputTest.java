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

}