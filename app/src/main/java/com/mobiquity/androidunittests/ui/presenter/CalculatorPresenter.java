package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;
import com.mobiquity.androidunittests.converter.ExpressionConverter;
import com.mobiquity.androidunittests.converter.SymbolToOperatorConverter;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@AppScope
public class CalculatorPresenter extends Presenter<CalculatorView> {

    private Calculator calculator;
    private ExpressionConverter expressionConverter;
    private List<String> expression;

    @Inject
    public CalculatorPresenter(Calculator calculator, ExpressionConverter inputsConverter) {
        this.calculator = calculator;
        this.expressionConverter = inputsConverter;
        expression = new ArrayList<>();
    }

    public void handleNumber(int number) {
        expression.add(Integer.toString(number));
        expression = expressionConverter.normalize(expression);
        view().updateDisplayText(getDisplayString());
    }

    public void handleOperator(String symbol) {
        expression.add(symbol);
        expression = expressionConverter.normalize(expression);
        view().updateDisplayText(getDisplayString());
    }

    public void evaluate() {
        List<Input> inputs = expressionConverter.convert(expression);
        int result = calculator.evaluate(inputs.toArray(new Input[inputs.size()]));
        view().showSuccessfulCalculation(Integer.toString(result));
    }

    private String getDisplayString() {
        String displayString = "";
        for(String item : expression) {
            displayString += item;
        }
        return displayString;
    }

}
