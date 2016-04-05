package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class LeftParenInputTest {

    private LeftParenInput leftParenInput;

    @Before
    public void setUp() {
        leftParenInput = new LeftParenInput();
    }

    @Test
    public void testLeftParen_InputType() {
        assertThat(leftParenInput.getType()).isEqualTo(InputType.LEFT_PAREN);
    }

    @Test
    public void testLeftParen_Value() {
        assertThat(leftParenInput.value).isEqualTo("(");
    }
}