package com.mobiquity.androidunittests.calculator.input;

import android.content.Context;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;
import com.mobiquity.androidunittests.util.ExpressionUtil;

import java.util.List;
import java.util.Map;

public class DecimalInput extends Input {
    public DecimalInput() {
        super(R.string.decimal_symbol, InputType.DEFAULT);
    }

    @Override
    public NormalizeRule getNormalizeExpressionRule(Map<String, Input> expressionInputMap, List<String> expression) {
        int lastDecimalIndex = expression.lastIndexOf(getValue());

        // Don't allow multiple decimals per number
        if (lastDecimalIndex == -1) {
            return NormalizeRule.ADD;
        } else if(lastDecimalIndex > -1 && ExpressionUtil.containsDigitsOrDecimalOnly(expression, getValue(), lastDecimalIndex)) {
            return NormalizeRule.REMOVE;
        }
        return NormalizeRule.ADD;
    }

    @Override
    public List<Input> addToCalculatorExpression(List<Input> calculatorExpression) {
        if(calculatorExpression.isEmpty()) {
            calculatorExpression.add(new NumericInput("0."));
        } else {
            Input lastInput = calculatorExpression.get(calculatorExpression.size()-1);
            if(lastInput instanceof NumericInput) {
                String previousValue = lastInput.getValue();
                calculatorExpression.remove(lastInput);
                String newValue = previousValue + getValue();
                calculatorExpression.add(new NumericInput(newValue));
            } else if(lastInput instanceof SubtractionOperator) {
                boolean isInputtingNegativeNumber = calculatorExpression.size() <= 1 ||
                        !(calculatorExpression.get(calculatorExpression.size() - 2) instanceof NumericInput);

                if(isInputtingNegativeNumber) {
                    String previousValue = lastInput.getValue();
                    calculatorExpression.remove(lastInput);
                    String newValue = previousValue + getValue();
                    calculatorExpression.add(new NumericInput(newValue));
                } else {
                    calculatorExpression.add(new NumericInput("0."));
                }
            } else {
                calculatorExpression.add(new NumericInput("0."));
            }
        }

        return calculatorExpression;
    }
}
