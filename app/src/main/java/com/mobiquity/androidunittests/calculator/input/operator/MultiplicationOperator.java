package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

public class MultiplicationOperator extends Operator {

    public MultiplicationOperator() {
        super("*", InputType.OPERATOR);
    }

    @Override
    public int execute(int param1, int param2) {
        return multiply(param1, param2);
    }

    @Override
    public int getPrecedence() {
        return Precedence.MULTIPLICATION_PRECEDENCE.getValue();
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }


    private int multiply(int param1, int param2) {
        return param1 * param2;
    }
}
