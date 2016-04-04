package com.mobiquity.androidunittests;

import com.mobiquity.androidunittests.input.Input;
import com.mobiquity.androidunittests.input.NumericInput;
import com.mobiquity.androidunittests.input.operator.AdditionOperator;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;

import static com.google.common.truth.Truth.*;

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

}