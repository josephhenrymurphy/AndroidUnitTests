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

    public static class CalculatorEvaluationException extends Exception {
        public CalculatorEvaluationException(String message) {
            super(message);
        }
    }


    @Inject
    public Calculator(InfixInputParser infixInputParser) {
        this.infixInputParser = infixInputParser;
    }

    public double evaluate(Input[] inputs) throws CalculatorEvaluationException {
        try {
            Queue<Input> postfixInputs = infixInputParser.toPostfix(inputs);

            // Return current result if there are no inputs to evaluate
            if (postfixInputs.isEmpty()) {
                return 0;
            }

            Stack<Double> stack = new Stack<>();
            for (Input input : postfixInputs) {
                switch (input.getType()) {
                    case NUMBER:
                        NumericInput numericInput = (NumericInput) input;
                        stack.push(numericInput.getValue());
                        break;
                    case OPERATOR:
                        Operator operator = (Operator) input;
                        double secondOperand = stack.pop();
                        double firstOperand = stack.pop();

                        double result = operator.isLeftAssociative() ?
                                operator.execute(firstOperand, secondOperand) :
                                operator.execute(secondOperand, firstOperand);

                        stack.push(result);
                        break;

                }
            }

            return stack.pop() + 0.0;
        } catch (EmptyStackException | InfixInputParser.InputParserException e) {
            throw new CalculatorEvaluationException("Invalid Expression");
        }
    }
}
