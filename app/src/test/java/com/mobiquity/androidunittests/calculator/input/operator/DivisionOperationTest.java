package com.mobiquity.androidunittests.calculator.input.operator;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class DivisionOperationTest {

    private DivisionOperation divisionOperation;

    @Before
    public void setUp() {
        divisionOperation = new DivisionOperation();
    }

    @Test
    public void testDivision_HasCorrectPrecedence() {
        assertThat(divisionOperation.getPrecedence()).isEqualTo(
                Operator.Precedence.DIVISION_PRECEDENCE.getValue());
    }

    @Test
    public void testDivision_IsLeftAssociative() {
        assertThat(divisionOperation.isLeftAssociative()).isTrue();
    }

    @Test
    public void testDivision_ValidNumbers() {
        int expectedResult = 5;
        assertThat(divisionOperation.execute(25,5)).isWithin(expectedResult);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivision_DivideByZero() {
        double result = divisionOperation.execute(5, 0);
        System.out.println(result);
    }

}