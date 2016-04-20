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

import timber.log.Timber;

public class CalculatorPresenter extends Presenter<CalculatorView> {

    private Calculator calculator;
    private ExpressionConverter expressionConverter;
    private List<String> expression;

    public CalculatorPresenter(Calculator calculator, ExpressionConverter inputsConverter) {
        this.calculator = calculator;
        this.expressionConverter = inputsConverter;
        expression = new ArrayList<>();
    }

    @Override
    public void bind(CalculatorView view) {
        super.bind(view);
        view().updateDisplayText(getDisplayString());
        evaluate(false);
    }

    public void handleNumber(int number) {
        expression.add(Integer.toString(number));
        expression = expressionConverter.normalize(expression);
        view().updateDisplayText(getDisplayString());
        evaluate(false);
    }

    public void handleOperator(String symbol) {
        expression.add(symbol);
        expression = expressionConverter.normalize(expression);
        view().updateDisplayText(getDisplayString());
        evaluate(false);
    }

    public void handleEvaluate() {
        evaluate(true);
    }

    private void evaluate(boolean isResult) {
        try {
            List<Input> inputs = expressionConverter.convert(expression);
            int result = calculator.evaluate(inputs.toArray(new Input[inputs.size()]));
            if (isResult) {
                expression.clear();
                expression.add(Integer.toString(result));
                view().showResult(Integer.toString(result));
            } else {
                if(inputs.size() > 1) {
                    view().showSuccessfulCalculation(Integer.toString(result));
                }
            }
        } catch (Calculator.CalculatorEvaluationException e) {
            Timber.e(e, e.getMessage());
            if(isResult) {
                view().showResultError();
            }
        }
    }

    private String getDisplayString() {
        String displayString = "";
        for(String item : expression) {
            displayString += item;
        }
        return displayString;
    }

}
