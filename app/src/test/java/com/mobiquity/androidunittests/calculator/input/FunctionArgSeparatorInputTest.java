package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

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

    @Test
    public void testEqual() {
        assertAbout(input()).that(functionArgSeparatorInput)
                .valueEqualTo(new FunctionArgSeparatorInput());
    }

    @Test
    public void testNotEqual() {
        assertAbout(input()).that(functionArgSeparatorInput)
                .valueNotEqualTo(new Input("dummy", InputType.FUNCTION) {});
    }

}