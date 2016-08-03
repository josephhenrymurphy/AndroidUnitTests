package com.mobiquity.androidunittests.calculator.input;

public class FunctionInput extends Input {

    private int numExpectedParams;

    public FunctionInput(int numExpectedParams) {
        super(InputType.FUNCTION);
        this.numExpectedParams = numExpectedParams;
    }

    public int getNumExpectedParams() {
        return numExpectedParams;
    }

    @Override
    public boolean valueEquals(Input input) {
        return super.valueEquals(input) &&
                ((FunctionInput)input).numExpectedParams == numExpectedParams;
    }
}
