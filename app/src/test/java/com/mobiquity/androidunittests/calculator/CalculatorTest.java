package com.mobiquity.androidunittests.calculator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class CalculatorTest {

    private Calculator calculator;
    private InfixInputParser infixInputParser;

    @Before
    public void setUp() {
        infixInputParser = new InfixInputParser();
        calculator = new Calculator(infixInputParser);
    }

    @Test
    public void testEvaluate_SimpleAdd() {
        // Test: 3 + 4 = 7
        int result = calculator.evaluate(new Input[]{
                new NumericInput(3),
                new AdditionOperator(),
                new NumericInput(4)
        });

        assertThat(result).isEqualTo(7);
    }

}