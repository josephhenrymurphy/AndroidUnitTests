package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class SubtractionOperatorTest {

    private SubtractionOperator subtractionOperator;

    @Before
    public void setUp() {
        subtractionOperator = new SubtractionOperator();
    }


    @Test
    public void testEquals_Symmetric() {
        SubtractionOperator operator1 = new SubtractionOperator();
        SubtractionOperator operator2 = new SubtractionOperator();
        assertThat(operator1).isEqualTo(operator1);
        assertThat(operator1).isEqualTo(operator2);
        assertThat(operator1.hashCode()).isEqualTo(operator2.hashCode());
    }

    @Test
    public void testNotEquals() {
        SubtractionOperator operator = new SubtractionOperator();
        assertThat(operator).isNotEqualTo(null);
        assertThat(operator).isNotEqualTo(new Object());
    }

    @Test
    public void testAdditionOperator_ShouldExtendOperator() {
        assertThat(subtractionOperator).isInstanceOf(Operator.class);
    }

    @Test
    public void testGetType_IsAnOperator() {
        assertThat(subtractionOperator.getType()).isEqualTo(InputType.OPERATOR);
    }
    @Test
    public void testGetPrecedence_IsEqualToAdditionPrecedence() {
        assertThat(subtractionOperator.getPrecedence())
                .isEqualTo(Operator.Precedence.SUBTRACTION_PRECEDENCE.getValue());
    }

    @Test
    public void testShouldBeLeftAssociative() {
        assertThat(subtractionOperator.isLeftAssociative()).isTrue();
    }

    @Test
    public void testExecute_ShouldSubtractOperands() {
        assertThat(subtractionOperator.execute(2,3)).isEqualTo(-1);
        assertThat(subtractionOperator.execute(0,10)).isEqualTo(-10);
        assertThat(subtractionOperator.execute(10,-10)).isEqualTo(20);
        assertThat(subtractionOperator.execute(-2,-3)).isEqualTo(1);
    }

}