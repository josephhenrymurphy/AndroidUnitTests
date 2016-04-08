package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

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


    @Test
    public void testEqual() {
        assertAbout(input()).that(leftParenInput)
                .valueEqualTo(new LeftParenInput());
    }

    @Test
    public void testNotEqual() {
        assertAbout(input()).that(leftParenInput)
                .valueNotEqualTo(new Input("dummy", InputType.FUNCTION) {});
    }
}