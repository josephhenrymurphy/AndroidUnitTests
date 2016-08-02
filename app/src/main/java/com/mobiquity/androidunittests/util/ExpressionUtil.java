package com.mobiquity.androidunittests.util;

import com.mobiquity.androidunittests.calculator.input.DecimalInput;

import java.util.List;

public class ExpressionUtil {

    public static boolean containsDigitsOrDecimalOnly(List<String> expression, String decimalSymbol, int startIndex) {
        for(int i = startIndex; i < expression.size(); i++) {
            boolean isDecimal = decimalSymbol.equals(expression.get(i));
            if(!isNumeric(expression.get(i)) && !isDecimal) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String input) {
        try {
            double d = Double.parseDouble(input);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
