package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class FunctionArgSeparatorInputTest {

    private FunctionArgSeparatorInput functionArgSeparatorInput;

    @Before
    public void setUp() {
        functionArgSeparatorInput = new FunctionArgSeparatorInput();
    }

    @Test
    public void testFunctionArgSeparator_InputType() {
        assertThat(functionArgSeparatorInput.getType())
                .isEqualTo(InputType.FUNCTION_ARG_SEPARATOR);
    }

    @Test
    public void testFunctionArgSeparator_Value() {
        assertThat(functionArgSeparatorInput.value)
                .isEqualTo(",");
    }

}