package com.mobiquity.androidunittests.calculator.input.operator;

public interface Operator {
    int execute(int param1, int param2);
    int getPrecedence();
    boolean isLeftAssociative();

    enum Precedence {
        ADDITION_PRECEDENCE(2),
        SUBTRACTION_PRECEDENCE(2),
        MULTIPLICATION_PRECEDENCE(3),
        DIVISION_PRECEDENCE(3),
        POWER_PRECENDENCE(4);

        private int value;

        Precedence(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
