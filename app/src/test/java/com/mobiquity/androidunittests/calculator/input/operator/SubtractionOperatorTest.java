package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.InputType;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

public class SubtractionOperatorTest {

    private SubtractionOperator subtractionOperator;

    @Before
    public void setUp() {
        subtractionOperator = new SubtractionOperator();
    }


    @Test
    public void testEquals() {
        SubtractionOperator operator1 = new SubtractionOperator();
        SubtractionOperator operator2 = new SubtractionOperator();
        assertAbout(input()).that(operator1).valueEqualTo(operator1);
        assertAbout(input()).that(operator1).valueEqualTo(operator2);
    }

    @Test
    public void testNotEquals() {
        SubtractionOperator operator1 = new SubtractionOperator();
        assertAbout(input()).that(operator1).valueNotEqualTo(null);
        assertAbout(input()).that(operator1).valueNotEqualTo(new Input(InputType.FUNCTION){});
    }

    @Test
    public void testSubtractionOperator_ShouldExtendOperator() {
        assertThat(subtractionOperator).isInstanceOf(Operator.class);
    }

    @Test
    public void testGetType_IsAnOperator() {
        assertThat(subtractionOperator.getType()).isEqualTo(InputType.OPERATOR);
    }
    @Test
    public void testGetPrecedence_IsEqualToSubtractionPrecedence() {
        assertThat(subtractionOperator.getPrecedence())
                .isEqualTo(Operator.Precedence.SUBTRACTION_PRECEDENCE.getValue());
    }

    @Test
    public void testShouldBeLeftAssociative() {
        assertThat(subtractionOperator.isLeftAssociative()).isTrue();
    }

    @Test
    public void testExecute_ShouldSubtractOperands() {
        assertThat(subtractionOperator.execute(2,3)).isWithin(-1);
        assertThat(subtractionOperator.execute(0,10)).isWithin(-10);
        assertThat(subtractionOperator.execute(10,-10)).isWithin(20);
        assertThat(subtractionOperator.execute(-2,-3)).isWithin(1);
    }

}