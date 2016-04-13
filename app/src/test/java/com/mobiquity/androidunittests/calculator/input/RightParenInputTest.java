package com.mobiquity.androidunittests.calculator.input;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

public class RightParenInputTest {

    private RightParenInput rightParenInput;

    @Before
    public void setUp() {
        rightParenInput = new RightParenInput();
    }

    @Test
    public void testRightParen_InputType() {
        assertThat(rightParenInput.getType()).isEqualTo(InputType.RIGHT_PAREN);
    }

    @Test
    public void testRightParen_Value() {
        assertThat(rightParenInput.value).isEqualTo(")");
    }


    @Test
    public void testEqual() {
        assertAbout(input()).that(rightParenInput)
                .valueEqualTo(new RightParenInput());
    }

    @Test
    public void testNotEqual() {
        assertAbout(input()).that(rightParenInput)
                .valueNotEqualTo(new Input("dummy", InputType.FUNCTION) {});
    }

}