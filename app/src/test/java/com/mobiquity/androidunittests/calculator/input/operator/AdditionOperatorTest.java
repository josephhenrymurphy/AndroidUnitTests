package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.InputType;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.common.truth.Truth.assertThat;
import static com.mobiquity.androidunittests.testutil.InputSubject.input;

public class AdditionOperatorTest {

    private AdditionOperator additionOperator;

    @Before
    public void setUp() {
        additionOperator = new AdditionOperator();
    }

    @Test
    public void testEqual() {
        AdditionOperator operator1 = new AdditionOperator();
        AdditionOperator operator2 = new AdditionOperator();
        assertAbout(input()).that(operator1).valueEqualTo(operator1);
        assertAbout(input()).that(operator1).valueEqualTo(operator2);
    }

    @Test
    public void testNotEquals() {
        AdditionOperator operator1 = new AdditionOperator();
        assertAbout(input()).that(operator1).valueNotEqualTo(null);
        assertAbout(input()).that(operator1).valueNotEqualTo(new Input("dummy", InputType.FUNCTION){});
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
        assertThat(additionOperator.execute(2,3)).isWithin(5);
        assertThat(additionOperator.execute(0,10)).isWithin(10);
        assertThat(additionOperator.execute(10,-10)).isWithin(0);
        assertThat(additionOperator.execute(-2,-3)).isWithin(-5);
    }

}