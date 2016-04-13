package com.mobiquity.androidunittests.calculator.input;

import org.junit.Test;

import java.security.InvalidParameterException;

import static com.google.common.truth.Truth.assertThat;

public class InputTest {

    private static class DummyInput extends Input {
        public DummyInput(String value, InputType type) {
            super(value, type);
        }
        public DummyInput(int value, InputType type) {
            super(value, type);
        }
    }

    @Test
    public void testSuperInputType_StringValue() {
        DummyInput dummyInput = new DummyInput("dummy", InputType.FUNCTION);
        assertThat(dummyInput.value).isEqualTo("dummy");
        assertThat(dummyInput.getType()).isEqualTo(InputType.FUNCTION);
    }

    @Test(expected = InvalidParameterException.class)
    public void testSuperInputType_InvalidStringValueForNumericInput() {
        new DummyInput("dummy", InputType.NUMBER);
    }

    @Test
    public void testSuperInputType_IntValue() {
        DummyInput dummyInput = new DummyInput(3, InputType.NUMBER);
        assertThat(dummyInput.value).isEqualTo(String.valueOf(3));
        assertThat(dummyInput.getType()).isEqualTo(InputType.NUMBER);

    }

    @Test(expected = InvalidParameterException.class)
    public void testSuperInputType_InvalidNumberValueForNonumericInput() {
        new DummyInput(3, InputType.FUNCTION);
    }

    @Test
    public void testDisplayString_ReturnsCorrectValue() {
        DummyInput dummyInput = new DummyInput("dummy", InputType.FUNCTION);
        assertThat(dummyInput.getDisplayString()).isEqualTo("dummy");
    }
}