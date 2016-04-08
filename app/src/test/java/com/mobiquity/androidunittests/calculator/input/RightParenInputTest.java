package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class RightParenInputTest {

    private RightParenInput rightParenInput;

    @Before
    public void setUp() {
        rightParenInput = new RightParenInput();
    }

    @Test
    public void testLeftParen_InputType() {
        assertThat(rightParenInput.getType()).isEqualTo(InputType.RIGHT_PAREN);
    }

    @Test
    public void testLeftParen_Value() {
        assertThat(rightParenInput.value).isEqualTo(")");
    }

}