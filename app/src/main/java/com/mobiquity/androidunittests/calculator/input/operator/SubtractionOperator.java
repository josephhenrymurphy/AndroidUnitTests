package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.InputType;

public class SubtractionOperator extends Operator {

    public SubtractionOperator() {
        super("-", InputType.OPERATOR);
    }

    @Override
    public int execute(int param1, int param2) {
        return subtract(param1, param2);
    }

    @Override
    public int getPrecedence() {
        return Precedence.SUBTRACTION_PRECEDENCE.getValue();
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    private int subtract(int minuend, int subtrahend) {
        return minuend - subtrahend;
    }

}
