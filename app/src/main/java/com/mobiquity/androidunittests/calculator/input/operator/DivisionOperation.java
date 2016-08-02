package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.R;

public class DivisionOperation extends Operator{

    public DivisionOperation() {
        super(R.string.divide_op);
    }

    @Override
    public double execute(double param1, double param2) {
        if(param2 == 0) {
            throw new ArithmeticException("Divide by zero");
        }
        return param1 / param2;
    }

    @Override
    public int getPrecedence() {
        return Precedence.DIVISION_PRECEDENCE.getValue();
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }
}
