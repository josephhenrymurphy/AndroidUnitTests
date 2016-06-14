package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

public class AdditionOperator extends Operator {

    public AdditionOperator() {
        super("+", InputType.OPERATOR);
    }

    @Override
    public double execute(double param1, double param2) {
        return add(param1, param2);
    }

    @Override
    public int getPrecedence() {
        return Precedence.ADDITION_PRECEDENCE.getValue();
    }

    @Override
    public boolean isLeftAssociative() {
        return true;
    }

    private double add(double firstAddend, double secondAddend) {
        return firstAddend + secondAddend;
    }

}
