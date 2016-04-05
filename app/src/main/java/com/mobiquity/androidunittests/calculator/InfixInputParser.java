package com.mobiquity.androidunittests.calculator;

import com.mobiquity.androidunittests.calculator.input.FunctionInput;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.InputType;
import com.mobiquity.androidunittests.calculator.input.LeftParenInput;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.inject.Inject;

public class InfixInputParser {

    public class InputParserException extends RuntimeException {
        public InputParserException(String message) {
            super(message);
        }
    }

    @Inject
    public InfixInputParser(){}

    /**
     * Takes an infix expression and returns postfix
     * using the shunting-yard algorithm
     */
    public Queue<Input> toPostfix(Input[] inputs) throws InputParserException {
        Queue<Input> outputQueue = new LinkedList<>();
        Stack<Input> operatorStack = new Stack<>();

        for (int index = 0; index < inputs.length; index++) {
            Input currentInput = inputs[index];
            switch (currentInput.getType()) {
                case NUMBER:
                    outputQueue.add(currentInput);
                    break;
                case FUNCTION:
                    operatorStack.push(currentInput);
                    break;
                case FUNCTION_ARG_SEPARATOR:
                    while(operatorStack.peek().getType() != InputType.LEFT_PAREN) {
                        Input operator = operatorStack.pop();
                        outputQueue.add(operator);

                        if(operatorStack.empty()) {
                            throw new InputParserException("Misplaced function argument separator.");
                        }
                    }
                    break;
                case OPERATOR:
                    Operator currentOperator = (Operator) currentInput;
                    if(!operatorStack.empty()) {
                        while (operatorStack.peek() instanceof Operator) {
                            Operator stackOperator = (Operator) operatorStack.peek();
                            boolean leftAssociativeCheck = currentOperator.isLeftAssociative() && currentOperator.getPrecedence() <= stackOperator.getPrecedence();
                            boolean rightAssociativeCheck = !currentOperator.isLeftAssociative() && currentOperator.getPrecedence() < stackOperator.getPrecedence();

                            if (leftAssociativeCheck || rightAssociativeCheck) {
                                outputQueue.add(operatorStack.pop());
                            }
                        }
                    }
                    operatorStack.push(currentInput);
                    break;
                case LEFT_PAREN:
                    operatorStack.push(currentInput);
                    break;
                case RIGHT_PAREN:
                    while(!(operatorStack.peek() instanceof LeftParenInput)) {
                        outputQueue.add(operatorStack.pop());
                    }
                    LeftParenInput leftParen = (LeftParenInput) operatorStack.pop();
                    if(operatorStack.peek() instanceof FunctionInput) {
                        outputQueue.add(operatorStack.pop());
                    }
                    break;
            }
        }

        // Add remaining operators to output
        while (!operatorStack.empty()) {
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }
}
