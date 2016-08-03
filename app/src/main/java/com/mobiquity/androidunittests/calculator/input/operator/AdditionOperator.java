package com.mobiquity.androidunittests.calculator.input.operator;

import android.support.annotation.StringRes;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.ExpressionConverter;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.LeftParenInput;

import java.util.List;
import java.util.Map;

public class AdditionOperator extends Operator {

    public AdditionOperator() {
        super(R.string.add_op);
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
