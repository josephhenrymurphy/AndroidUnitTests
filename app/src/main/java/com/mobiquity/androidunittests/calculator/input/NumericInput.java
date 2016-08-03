package com.mobiquity.androidunittests.calculator.input;

import com.mobiquity.androidunittests.calculator.CalculatorConstants;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;

import java.util.List;

public class NumericInput extends Input {

    String numericValue;

    public NumericInput(String value) {
        super(InputType.NUMBER);
        numericValue = value;
    }

    public NumericInput(double value) {
        super(InputType.NUMBER);
        numericValue = CalculatorConstants.DECIMAL_FORMAT.format(value);
    }

    @Override
    public String getValue() {
        return numericValue;
    }

    public double getValueAsDouble() {
        return Double.parseDouble(numericValue);
    }

    @Override
    public boolean valueEquals(Input input) {
        return super.valueEquals(input) &&
                Double.valueOf(((NumericInput) input).getValueAsDouble())
                        .equals(getValueAsDouble());
    }

    @Override
    public List<Input> addToCalculatorExpression(List<Input> calculatorExpression) {
        if(calculatorExpression.isEmpty()) {
            calculatorExpression.add(this);
        } else {
            Input lastInput = calculatorExpression.get(calculatorExpression.size()-1);

            if(lastInput instanceof NumericInput) {
                calculatorExpression.remove(lastInput);
                String lastValue = lastInput.getValue();
                String newValue = lastValue + getValue();
                calculatorExpression.add(new NumericInput(newValue));
            }

            // Handle inputting negative numbers
            else if(lastInput instanceof SubtractionOperator) {
                boolean isInputtingNegativeNumber = calculatorExpression.size() <= 1 ||
                        !(calculatorExpression.get(calculatorExpression.size() - 2) instanceof NumericInput);

                if(isInputtingNegativeNumber) {
                    int lastItemIndex = calculatorExpression.size()-1;
                    calculatorExpression.remove(lastItemIndex);
                    String lastValue = lastInput.getValue();
                    String newValue = lastValue + getValue();
                    calculatorExpression.add(new NumericInput(newValue));
                } else {
                    calculatorExpression.add(this);
                }
            } else {
                calculatorExpression.add(this);
            }
        }

        return calculatorExpression;
    }
}
