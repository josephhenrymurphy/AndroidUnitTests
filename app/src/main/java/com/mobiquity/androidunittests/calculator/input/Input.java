package com.mobiquity.androidunittests.calculator.input;

import android.support.annotation.StringRes;

import com.mobiquity.androidunittests.CalculatorApplication;

import java.util.List;
import java.util.Map;

public abstract class Input {

    private InputType type;
    protected int symbolRes;

    protected Input(InputType type) {
        this(-1, type);
    }

    protected Input(@StringRes int symbolRes, InputType type) {
        this.symbolRes = symbolRes;
        this.type = type;
    }

    public String getValue() {
        return CalculatorApplication.getStringFromRes(symbolRes);
    }

    public InputType getType() {
        return type;
    }

    public boolean valueEquals(Input input) {
        Class clazz = getClass();
        return clazz.isInstance(input);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getValue();
    }

    public NormalizeRule getNormalizeExpressionRule(Map<String, Input> expressionInputMap, List<String> expression) {
        return NormalizeRule.ADD;
    }

    public List<Input> addToCalculatorExpression(List<Input> calculatorExpression) {
        calculatorExpression.add(this);
        return calculatorExpression;
    }

    public enum NormalizeRule {
        ADD, REPLACE, REMOVE
    }

}
