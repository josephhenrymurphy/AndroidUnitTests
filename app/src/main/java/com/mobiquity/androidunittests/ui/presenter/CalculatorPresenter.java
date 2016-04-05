package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;
import com.mobiquity.androidunittests.converter.SymbolToOperatorConverter;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;
import com.mobiquity.androidunittests.ui.mvpview.MvpView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@AppScope
public class CalculatorPresenter extends Presenter<CalculatorView> {

    private Calculator calculator;
    private SymbolToOperatorConverter operatorConverter;
    private List<Input> inputs;

    @Inject
    public CalculatorPresenter(Calculator calculator, SymbolToOperatorConverter operatorConverter) {
        this.calculator = calculator;
        this.operatorConverter = operatorConverter;
        inputs = new ArrayList<>();
    }

    public void handleNumber(int number) {
        if(!inputs.isEmpty() &&
                inputs.get(inputs.size()-1) instanceof NumericInput) {
            NumericInput lastNumber = (NumericInput) inputs.get(inputs.size()-1);
            String numberString = String.valueOf(lastNumber.getValue());
            numberString += number;
            inputs.remove(lastNumber);
            inputs.add(new NumericInput(Integer.parseInt(numberString)));
        } else {
            inputs.add(new NumericInput(number));
        }
        view().updateDisplayText(inputsToDisplayString());
    }

    public void handlerOperator(String symbol) {
        if(!inputs.isEmpty() &&
                inputs.get(inputs.size()-1) instanceof Operator) {
            inputs.remove(inputs.size()-1);
        }
        inputs.add(operatorConverter.convert(symbol));
        view().updateDisplayText(inputsToDisplayString());


    }

    private String inputsToDisplayString() {
        String displayString = "";

        for(Input input : inputs) {
            displayString += input.getDisplayString();
        }
        return displayString;
    }


}
