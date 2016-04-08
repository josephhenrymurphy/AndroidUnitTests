package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;

public class AdditionOperator extends Operator {

    public AdditionOperator() {
        super("+", InputType.OPERATOR);
    }

    @Override
    public int execute(int param1, int param2) {
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

    private int add(int firstAddend, int secondAddend) {
        return firstAddend + secondAddend;
    }

}
