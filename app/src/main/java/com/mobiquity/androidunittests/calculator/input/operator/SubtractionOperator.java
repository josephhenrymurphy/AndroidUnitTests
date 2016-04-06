package com.mobiquity.androidunittests.calculator.input.operator;

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

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if (o == null || !(o instanceof SubtractionOperator)) {
            return false;
        } else {
            SubtractionOperator operator = (SubtractionOperator) o;
            return this.value.equals(operator.value) &&
                    this.isLeftAssociative() == operator.isLeftAssociative() &&
                    this.getPrecedence() == operator.getPrecedence() &&
                    this.getType().equals(operator.getType());
        }
    }
}
