package com.mobiquity.androidunittests.calculator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;
import com.mobiquity.androidunittests.di.scopes.AppScope;

import java.util.EmptyStackException;
import java.util.Queue;
import java.util.Stack;

import javax.inject.Inject;

import timber.log.Timber;

@AppScope
public class Calculator {

    private InfixInputParser infixInputParser;
    private int currentResult;

    @Inject
    public Calculator(InfixInputParser infixInputParser) {
        this.infixInputParser = infixInputParser;
        currentResult = 0;
    }

    public int evaluate(Input[] inputs) {
        Queue<Input> postfixInputs = infixInputParser.toPostfix(inputs);

        // Return current result if there are no inputs to evaluate
        if(postfixInputs.isEmpty()) {
            return currentResult;
        }

        Stack<Integer> stack = new Stack<>();
        for(Input input : postfixInputs) {
            switch (input.getType()) {
                case NUMBER:
                    NumericInput numericInput = (NumericInput) input;
                    stack.push(numericInput.getValue());
                    break;
                case OPERATOR:
                    try {
                        Operator operator = (Operator) input;
                        int secondOperand = stack.pop();
                        int firstOperand = stack.pop();

                        int result = operator.isLeftAssociative() ?
                                operator.execute(firstOperand, secondOperand) :
                                operator.execute(secondOperand, firstOperand);

                        stack.push(result);
                        break;
                    } catch (EmptyStackException e) {
                        Timber.e(e, e.getMessage());
                    }
            }
        }

        currentResult = stack.pop();
        return currentResult;
    }



}
