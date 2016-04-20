package com.mobiquity.androidunittests.calculator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.MultiplicationOperator;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;

import static com.google.common.truth.Truth.assertThat;

public class InfixInputParserTest {


    private InfixInputParser infixInputParser;

    @Before
    public void setUp() {
        infixInputParser = new InfixInputParser();
    }

    @Test
    public void testToPostfix_3_plus_4() {
        NumericInput threeInput = new NumericInput(3);
        AdditionOperator additionOperator = new AdditionOperator();
        NumericInput fourInput = new NumericInput(4);

        Input[] inputs = new Input[]{
                threeInput,
                additionOperator,
                fourInput
        };

        Queue<Input> postfixOutput = infixInputParser.toPostfix(inputs);
        assertThat(postfixOutput).containsExactly(threeInput, fourInput, additionOperator).inOrder();
    }

    @Test
    public void testToPostfix_MultipleAdd() {
        NumericInput threeInput = new NumericInput(3);
        NumericInput fourInput = new NumericInput(4);
        NumericInput fiveInput = new NumericInput(5);
        AdditionOperator additionOperator = new AdditionOperator();


        Input[] inputs = new Input[] {
                threeInput,
                additionOperator,
                fourInput,
                additionOperator,
                fiveInput
        };

        Queue<Input> postfixOutput = infixInputParser.toPostfix(inputs);
        assertThat(postfixOutput).containsExactly(threeInput, fourInput, additionOperator, fiveInput, additionOperator);
    }

    @Test
    public void testToPostfix_AdditionMultiplication() {
        NumericInput three = new NumericInput(3);
        NumericInput four = new NumericInput(4);
        NumericInput five = new NumericInput(5);
        AdditionOperator plus = new AdditionOperator();
        MultiplicationOperator times = new MultiplicationOperator();

        Input[] inputs = new Input[] {
                three,
                plus,
                four,
                times,
                five
        };

        Queue<Input> postfixOutput = infixInputParser.toPostfix(inputs);
        assertThat(postfixOutput).containsExactly(three, four, five, times, plus);
    }

    @Test
    public void testToPostfix_MultiplicationAddition() {
        NumericInput three = new NumericInput(3);
        NumericInput four = new NumericInput(4);
        NumericInput five = new NumericInput(5);
        AdditionOperator plus = new AdditionOperator();
        MultiplicationOperator times = new MultiplicationOperator();

        Input[] inputs = new Input[] {
                three,
                times,
                four,
                plus,
                five
        };

        Queue<Input> postfixOutput = infixInputParser.toPostfix(inputs);
        assertThat(postfixOutput).containsExactly(three, four, five, plus, times);
    }

}