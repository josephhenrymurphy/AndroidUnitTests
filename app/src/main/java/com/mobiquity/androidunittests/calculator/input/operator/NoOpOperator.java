package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

/**
 * A dummy operator that just returns the value of the first parameter
 */
public class NoOpOperator extends Operator {

    public NoOpOperator() {
        super(-1);
    }

    @Override
    public double execute(double param1, double param2) {
        return param1;
    }

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

}
