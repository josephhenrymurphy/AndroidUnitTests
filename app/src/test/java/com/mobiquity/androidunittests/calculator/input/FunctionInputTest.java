package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class FunctionInputTest {

    private FunctionInput functionInput;
    private static final int NUM_PARAMS = 2;

    @Before
    public void setUp() {
        functionInput = new FunctionInput("dummyFunction", NUM_PARAMS);
    }

    @Test
    public void testFunction_InputType() {
        assertThat(functionInput.getType()).isEqualTo(InputType.FUNCTION);
    }

    @Test
    public void testFunction_Value() {
        assertThat(functionInput.value).isEqualTo("dummyFunction");
    }

    @Test
    public void testFunction_ExpectedNumberOfParams() {
        assertThat(functionInput.getNumExpectedParams()).isEqualTo(NUM_PARAMS);
    }

}