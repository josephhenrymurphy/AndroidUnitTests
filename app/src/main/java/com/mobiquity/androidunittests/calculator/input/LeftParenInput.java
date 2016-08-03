package com.mobiquity.androidunittests.calculator.input;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.operator.MultiplicationOperator;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;

import java.util.List;

public class LeftParenInput extends Input {
    public LeftParenInput() {
        super(R.string.left_paren, InputType.LEFT_PAREN);
    }

    @Override
    public List<Input> addToCalculatorExpression(List<Input> calculatorExpression) {
        if(!calculatorExpression.isEmpty()) {
            Input lastInput = calculatorExpression.get(calculatorExpression.size()-1);
            if(!(lastInput instanceof Operator ||
                    lastInput instanceof FunctionInput ||
                    lastInput instanceof LeftParenInput)) {
                calculatorExpression.add(new MultiplicationOperator());
            }
        }
        calculatorExpression.add(this);
        return calculatorExpression;
    }
}
