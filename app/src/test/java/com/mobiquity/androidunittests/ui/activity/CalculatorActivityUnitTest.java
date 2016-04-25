package com.mobiquity.androidunittests.ui.activity;

import android.widget.Button;

import com.mobiquity.androidunittests.CustomGradleRunner;
import com.mobiquity.androidunittests.R;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import butterknife.ButterKnife;

import static com.google.common.truth.Truth.assertThat;

@RunWith(CustomGradleRunner.class)
public class CalculatorActivityUnitTest {

    private CalculatorActivity calculatorActivity;
    private ActivityController<CalculatorActivity> activityController;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(CalculatorActivity.class).setup();
        calculatorActivity = activityController.get();
    }

    @Test
    public void testPresenterBound_OnResume() {
        Mockito.verify(calculatorActivity.presenter).bind(calculatorActivity);
    }

    @Test
    public void testPresenterUnbound_OnPause() {
        activityController.pause();
        Mockito.verify(calculatorActivity.presenter).unbind();
    }

    @Test
    public void testUpdateDisplay_ChangesDisplayText() {
        String expectedDisplayText = "1+1";
        calculatorActivity.updateDisplayText(expectedDisplayText);

        Assertions.assertThat(calculatorActivity.displayInput)
                .hasText(expectedDisplayText);
    }

    @Test
    public void testShowSuccessfulCalculation_ChangesResultText() {
        String expectedResultText = "12";
        calculatorActivity.showSuccessfulCalculation(expectedResultText);

        Assertions.assertThat(calculatorActivity.resultText)
                .hasText(expectedResultText);
    }

    @Test
    public void testShowResult_UpdatesDisplayAndResultText() throws Exception {
        String expectedResult = "14";

        Robolectric.getForegroundThreadScheduler().pause();
        calculatorActivity.showResult(expectedResult);
        Assertions.assertThat(calculatorActivity.resultText).hasText(expectedResult);

        Robolectric.flushForegroundThreadScheduler();
        Assertions.assertThat(calculatorActivity.displayInput).hasText(expectedResult);
        Assertions.assertThat(calculatorActivity.resultText).isEmpty();
    }

        @Test
    public void testShowResultError_ShouldDisplayErrorMessage() {
        calculatorActivity.showResultError();
        String latestToast = ShadowToast.getTextOfLatestToast();

        assertThat(latestToast).isEqualTo(calculatorActivity.getString(R.string.invalid_expression));
    }

    @Test
    public void testOnClickNumber_StartsHandlingNumbers() throws Exception {
        for(Button button : calculatorActivity.numericPad.getNumericButtons()) {
            ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
            int expectedNumber = Integer.parseInt(button.getText().toString());
            button.performClick();
            Mockito.verify(calculatorActivity.presenter, Mockito.atLeastOnce()).handleNumber(captor.capture());

            assertThat(expectedNumber).isEqualTo(captor.getValue());
        }
    }

    @Test
    public void testOnClickOperatorButtons_StartsHandlingOperators() {
        for(Button button : calculatorActivity.operatorButtons) {
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            String expectedOperator = button.getText().toString();
            button.performClick();
            Mockito.verify(calculatorActivity.presenter, Mockito.atLeastOnce()).handleOperator(captor.capture());

            assertThat(expectedOperator).isEqualTo(captor.getValue());
        }
    }

    @Test
    public void testOnClickSymbolButtons_StartsHandlingSymbols() {
        for(Button button : calculatorActivity.symbolButtons) {
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            String expectedSymbol = button.getText().toString();
            button.performClick();
            Mockito.verify(calculatorActivity.presenter, Mockito.atLeastOnce()).handleSymbol(captor.capture());

            assertThat(expectedSymbol).isEqualTo(captor.getValue());
        }
    }

    @Test
    public void testOnClickEqualsButton_StartsExpressionEvaluation() {
        ButterKnife.findById(calculatorActivity, R.id.eq).performClick();
        Mockito.verify(calculatorActivity.presenter).handleEvaluate();
    }

}