package com.mobiquity.androidunittests.converter;

import android.content.Context;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.LeftParenInput;
import com.mobiquity.androidunittests.calculator.input.RightParenInput;

import javax.inject.Inject;

public class SymbolToInputConverter {

    private Context context;

    @Inject
    public SymbolToInputConverter(Context context) {
        this.context = context;
    }

    public Input convert(String symbol) {
        if(symbol.equals(context.getString(R.string.left_paren))) {
            return new LeftParenInput();
        } else if (symbol.equals(context.getString(R.string.right_paren))) {
            return new RightParenInput();
        } else {
            return null;
        }
    }
}
