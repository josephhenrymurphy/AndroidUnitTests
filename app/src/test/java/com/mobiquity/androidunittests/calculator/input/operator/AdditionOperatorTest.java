package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class AdditionOperatorTest {

    private AdditionOperator additionOperator;

    @Before
    public void setUp() {
        additionOperator = new AdditionOperator();
    }

    @Test
    public void testEquals_Symmetric() {
        AdditionOperator operator1 = new AdditionOperator();
        AdditionOperator operator2 = new AdditionOperator();
        assertThat(operator1).isEqualTo(operator1);
        assertThat(operator1).isEqualTo(operator2);
        assertThat(operator1.hashCode()).isEqualTo(operator2.hashCode());
    }

    @Test
    public void testNotEquals() {
        AdditionOperator operator = new AdditionOperator();
        assertThat(operator).isNotEqualTo(null);
        assertThat(operator).isNotEqualTo(new Object());
    }

    @Test
    public void testAdditionOperator_ShouldExtendOperator() {
        assertThat(additionOperator).isInstanceOf(Operator.class);
    }

    @Test
    public void testGetType_IsAnOperator() {
        assertThat(additionOperator.getType()).isEqualTo(InputType.OPERATOR);
    }
    @Test
    public void testGetPrecedence_IsEqualToAdditionPrecedence() {
        assertThat(additionOperator.getPrecedence())
                .isEqualTo(Operator.Precedence.ADDITION_PRECEDENCE.getValue());
    }

    @Test
    public void testShouldBeLeftAssociative() {
        assertThat(additionOperator.isLeftAssociative()).isTrue();
    }

    @Test
    public void testExecute_ShouldAddOperands() {
        assertThat(additionOperator.execute(2,3)).isEqualTo(5);
        assertThat(additionOperator.execute(0,10)).isEqualTo(10);
        assertThat(additionOperator.execute(10,-10)).isEqualTo(0);
        assertThat(additionOperator.execute(-2,-3)).isEqualTo(-5);
    }

}