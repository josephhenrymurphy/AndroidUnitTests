package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

public class SubtractionOperator extends Operator {

    public SubtractionOperator() {
        super("-", InputType.OPERATOR);
    }

    @Override
    public double execute(double param1, double param2) {
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

    private double subtract(double minuend, double subtrahend) {
        return minuend - subtrahend;
    }

}
