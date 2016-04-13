package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

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


    @Test
    public void testEqual() {
        assertAbout(input()).that(functionInput)
                .valueEqualTo(new FunctionInput("dummyFunction", NUM_PARAMS));
    }

    @Test
    public void testNotEqual() {
        final int wrongParams = 4;
        assertAbout(input()).that(functionInput)
                .valueNotEqualTo(new FunctionInput("bad", NUM_PARAMS));
        assertAbout(input()).that(functionInput)
                .valueNotEqualTo(new FunctionInput("dummyFunction", wrongParams));
    }

}