package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.InputType;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

public class MultiplicationOperatorTest {

    private MultiplicationOperator multiplicationOperator;

    @Before
    public void setUp() {
        multiplicationOperator = new MultiplicationOperator();
    }


    @Test
    public void testEquals() {
        MultiplicationOperator operator1 = new MultiplicationOperator();
        MultiplicationOperator operator2 = new MultiplicationOperator();
        assertAbout(input()).that(operator1).valueEqualTo(operator1);
        assertAbout(input()).that(operator1).valueEqualTo(operator2);
    }

    @Test
    public void testNotEquals() {
        MultiplicationOperator operator1 = new MultiplicationOperator();
        assertAbout(input()).that(operator1).valueNotEqualTo(null);
        assertAbout(input()).that(operator1).valueNotEqualTo(new Input("dummy", InputType.FUNCTION){});
    }

    @Test
    public void testMultiplicationOperator_ShouldExtendOperator() {
        assertThat(multiplicationOperator).isInstanceOf(Operator.class);
    }

    @Test
    public void testGetType_IsAnOperator() {
        assertThat(multiplicationOperator.getType()).isEqualTo(InputType.OPERATOR);
    }
    @Test
    public void testGetPrecedence_IsEqualToMultiplicationPrecedence() {
        assertThat(multiplicationOperator.getPrecedence())
                .isEqualTo(Operator.Precedence.MULTIPLICATION_PRECEDENCE.getValue());
    }

    @Test
    public void testShouldBeLeftAssociative() {
        assertThat(multiplicationOperator.isLeftAssociative()).isTrue();
    }

    @Test
    public void testExecute_ShouldMultiplyOperands() {
        assertThat(multiplicationOperator.execute(2,3)).isEqualTo(6);
        assertThat(multiplicationOperator.execute(0,10)).isEqualTo(0);
        assertThat(multiplicationOperator.execute(10,-10)).isEqualTo(-100);
        assertThat(multiplicationOperator.execute(-2,-3)).isEqualTo(6);
    }

}