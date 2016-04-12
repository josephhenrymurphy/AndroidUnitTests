package com.mobiquity.androidunittests.converter;

import android.content.Context;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.di.scopes.AppScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StringExpressionToInputsConverter {

    private Context context;

    @Inject
    public StringExpressionToInputsConverter(Context context, SymbolToOperatorConverter operatorConverter) {
        this.context = context;
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
                }
            }

        }

        return inputs;
    }

    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isLastItemNumeric(List<Input> inputs) {
        return !inputs.isEmpty() &&
                inputs.get(inputs.size()-1) instanceof NumericInput;
    }


}
