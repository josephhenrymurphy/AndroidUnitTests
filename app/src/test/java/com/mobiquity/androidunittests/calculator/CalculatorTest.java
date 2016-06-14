package com.mobiquity.androidunittests.calculator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.MultiplicationOperator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.LinkedList;

import static com.google.common.truth.Truth.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    @Mock InfixInputParser infixInputParser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        calculator = new Calculator(infixInputParser);
    }

    @Test
    public void testEvaluate_SimpleAdd() throws Exception{
        // Test: 3 + 4 = 7
        Input input[] = new Input[]{
                new NumericInput(3),
                new AdditionOperator(),
                new NumericInput(4)
        };
        int expectedResult = 7;

        // Assume the infix input parser works and provide the correct postfix for the input
        Input[] postfixInput = new Input[] {
                new NumericInput(3),
                new NumericInput(4),
                new AdditionOperator()
        };
        mockPostFix(input, postfixInput);

        int result = calculator.evaluate(input);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testEvaluate_MultipleAddSubtract() throws Exception{
        //Test: 1 + 1 - 5 = -3
        Input input[] = new Input[]{
                new NumericInput(1),
                new AdditionOperator(),
                new NumericInput(1),
                new SubtractionOperator(),
                new NumericInput(5)
        };
        int expectedResult = -3;

        // Assume the infix input parser works and provide the correct postfix for the input
        Input[] postfixInput = new Input[] {
                new NumericInput(1),
                new NumericInput(1),
                new AdditionOperator(),
                new NumericInput(5),
                new SubtractionOperator()
        };
        mockPostFix(input, postfixInput);

        int result = calculator.evaluate(input);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testEvaluate_AdditionMultiply() throws Exception {
        //Test: 1 + 2 * 3 = 7

        Input input[] = new Input[]{
                new NumericInput(1),
                new AdditionOperator(),
                new NumericInput(2),
                new MultiplicationOperator(),
                new NumericInput(3)
        };
        int expectedResult = 7;

        // Assume the infix input parser works and provide the correct postfix for the input
        Input[] postfixInput = new Input[] {
                new NumericInput(1),
                new NumericInput(2),
                new NumericInput(3),
                new MultiplicationOperator(),
                new AdditionOperator()
        };
        mockPostFix(input, postfixInput);

        int result = calculator.evaluate(input);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test(expected = Calculator.CalculatorEvaluationException.class)
    public void testEvaluate_InvalidExpression() throws Exception {
        Input[] input = new Input[] {
                new NumericInput(1),
                new AdditionOperator()
        };

        Input[] postfixInpnut = new Input[] {
                new NumericInput(1),
                new AdditionOperator()
        };

        mockPostFix(input, postfixInpnut);
        calculator.evaluate(input);
    }

    @Test
    public void testEvaluate_EmptyExpressionReturnsZero() throws Exception {
        Input[] input = new Input[]{};
        mockPostFix(input, input);
        int result = calculator.evaluate(input);
        assertThat(result).isEqualTo(0);
    }

    private void mockPostFix(Input[] input, Input[] postfixInput) {
        Mockito.when(infixInputParser.toPostfix(input)).thenReturn(
                new LinkedList<>(Arrays.asList(postfixInput))
        );
    }

}