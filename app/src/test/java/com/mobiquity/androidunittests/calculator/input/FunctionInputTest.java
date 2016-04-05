package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class FunctionInputTest {

    private FunctionInput functionInput;

    @Before
    public void setUp() {
        functionInput = new FunctionInput("dummyFunction");
    }

    @Test
    public void testFunction_InputType() {
        assertThat(functionInput.getType()).isEqualTo(InputType.FUNCTION);
    }

    @Test
    public void testFunction_Value() {
        assertThat(functionInput.value).isEqualTo("dummyFunction");
    }

}