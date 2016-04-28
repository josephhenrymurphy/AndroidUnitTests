package com.mobiquity.androidunittests.ui.mvpview;

public interface CalculatorView extends MvpView {
    void updateDisplayText(String displayText);
    void showPassiveCalculation(String result);
    void showResult(String result);
    void showResultError();
}
