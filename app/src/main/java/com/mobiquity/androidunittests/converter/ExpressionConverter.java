package com.mobiquity.androidunittests.converter;

import android.content.Context;

import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.MultiplicationOperator;
import com.mobiquity.androidunittests.calculator.input.operator.NoOpOperator;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ExpressionConverter {

    private SymbolToOperatorConverter operatorConverter;

    @Inject
    public ExpressionConverter(SymbolToOperatorConverter operatorConverter) {
        this.operatorConverter = operatorConverter;
    }

    public List<String> normalize(List<String> expression) {
        List<String> normalizedExpression = new ArrayList<>();
        for(int i = 0; i < expression.size(); i++) {
            String item = expression.get(i);
            if(isOperator(item)) {
                Operator operator = operatorConverter.convert(item);
                if(operator instanceof AdditionOperator || operator instanceof MultiplicationOperator) {
                    // Don't allow leading operator
                    if (normalizedExpression.isEmpty()) {
                        continue;
                    }

                    // Don't allow multiple operators
                    while (!normalizedExpression.isEmpty() &&
                            isOperator(normalizedExpression.get(normalizedExpression.size()-1))) {
                        normalizedExpression.remove(normalizedExpression.size()-1);
                    }
                    normalizedExpression.add(item);
                } else if(operator instanceof SubtractionOperator) {
                    // Don't allow -- or +-
                    while (!normalizedExpression.isEmpty() &&
                            isOperator(normalizedExpression.get(normalizedExpression.size()-1))) {
                        Operator lastOperator = operatorConverter.convert(normalizedExpression.get(normalizedExpression.size()-1));
                        if(lastOperator instanceof AdditionOperator || lastOperator instanceof SubtractionOperator) {
                            normalizedExpression.remove(normalizedExpression.size() - 1);
                        } else {
                            break;
                        }
                    }
                    normalizedExpression.add(item);
                }

            } else {
                normalizedExpression.add(item);
            }
        }
        return normalizedExpression;
    }

    public List<Input> convert(List<String> expression) {
        List<Input> inputs = new ArrayList<>();
        for(String input : expression) {
            if(isNumeric(input)) {
                if(isLastItemNumeric(inputs)) {
                    NumericInput lastNumber = (NumericInput) inputs.get(inputs.size()-1);
                    inputs.remove(lastNumber);
                    String numberString = lastNumber.getValue() + input;
                    inputs.add(new NumericInput(Integer.parseInt(numberString)));
                } else if(isInputtingNegativeNumber(inputs)){
                    inputs.remove(inputs.get(inputs.size()-1));
                    String numberString = "-" + input;
                    inputs.add(new NumericInput(Integer.parseInt(numberString)));
                } else {
                    inputs.add(new NumericInput(Integer.parseInt(input)));
                }
            } else if(isOperator(input)) {
                inputs.add(operatorConverter.convert(input));
            }
        }

        return inputs;
    }

    private boolean isNumeric(String input) {
        try {
            double d = Double.parseDouble(input);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isOperator(String input) {
        return !(operatorConverter.convert(input) instanceof NoOpOperator);
    }

    private boolean isLastItemNumeric(List<Input> inputs) {
        return !inputs.isEmpty() &&
                inputs.get(inputs.size()-1) instanceof NumericInput;
    }

    private boolean isInputtingNegativeNumber(List<Input> inputs) {
        if(inputs.isEmpty()) {
            return false;
        } else if(inputs.size() == 1 && inputs.get(0) instanceof SubtractionOperator) {
            return true;
        } else if(inputs.size() > 2 &&
                inputs.get(inputs.size()-1) instanceof SubtractionOperator &&
                inputs.get(inputs.size()-2) instanceof Operator) {
            return true;
        } else {
            return false;
        }
    }

}
