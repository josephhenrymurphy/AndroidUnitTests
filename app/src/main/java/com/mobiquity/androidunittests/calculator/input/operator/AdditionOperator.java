package com.mobiquity.androidunittests.calculator.input.operator;

import com.mobiquity.androidunittests.calculator.input.InputType;
import com.mobiquity.androidunittests.util.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if (o == null || !(o instanceof AdditionOperator)) {
            return false;
        } else {
            AdditionOperator operator = (AdditionOperator) o;
            return this.value.equals(operator.value) &&
                    this.isLeftAssociative() == operator.isLeftAssociative() &&
                    this.getPrecedence() == operator.getPrecedence() &&
                    this.getType().equals(operator.getType());
        }
    }


}
