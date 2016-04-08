package com.mobiquity.androidunittests.converter;

import android.content.Context;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.NoOpOperator;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;

import javax.inject.Inject;


public class SymbolToOperatorConverter {

    private Context context;

    @Inject
    public SymbolToOperatorConverter(Context context) {
        this.context = context;
    }

    public Operator convert(String symbol) {
        if(symbol.equals(context.getString(R.string.add_op))) {
            return new AdditionOperator();
        } else if(symbol.equals(context.getString(R.string.substract_op))){
            return new SubtractionOperator();
        } else {
            return new NoOpOperator();
        }
    }

}
