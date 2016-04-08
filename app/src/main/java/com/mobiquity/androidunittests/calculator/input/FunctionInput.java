package com.mobiquity.androidunittests.calculator.input;

public class FunctionInput extends Input {

    private int numExpectedParams;

    public FunctionInput(String functionName, int numExpectedParams) {
        super(functionName, InputType.FUNCTION);
        this.numExpectedParams = numExpectedParams;
    }

    public int getNumExpectedParams() {
        return numExpectedParams;
    }
}
