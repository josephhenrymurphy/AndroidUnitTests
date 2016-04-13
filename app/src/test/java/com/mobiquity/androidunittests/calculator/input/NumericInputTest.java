package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

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
    public void testEqual() {
        NumericInput first = new NumericInput(1);
        NumericInput second = new NumericInput(1);

        assertAbout(input()).that(first).valueEqualTo(first);
        assertAbout(input()).that(first).valueEqualTo(second);
    }

    @Test
    public void testNotEqual() {
        NumericInput first = new NumericInput(1);
        NumericInput second = new NumericInput(2);
        Input dummyInput = new Input("dummy", InputType.FUNCTION){};

        assertAbout(input()).that(first).valueNotEqualTo(null);
        assertAbout(input()).that(first).valueNotEqualTo(second);
        assertAbout(input()).that(first).valueNotEqualTo(dummyInput);
    }


}