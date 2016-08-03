package com.mobiquity.androidunittests.ui.activity;

import android.widget.Button;

import com.google.common.truth.Truth;
import com.mobiquity.androidunittests.R;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import butterknife.ButterKnife;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
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

        Truth.assertThat(calculatorActivity.displayInput.getText().toString())
                .isEqualTo(expectedDisplayText);
    }

    @Test
    public void testShowSuccessfulCalculation_ChangesResultText() {
        String expectedResultText = "12";
        calculatorActivity.showPassiveCalculation(expectedResultText);

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
        assertThat(calculatorActivity.displayInput.getText().toString())
                .isEqualTo(expectedResult);
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
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            String buttonText = button.getText().toString();
            int expectedNumber = Integer.parseInt(buttonText);

            button.performClick();
            Mockito.verify(calculatorActivity.presenter, Mockito.atLeastOnce()).handleCalculatorButtonPress(captor.capture());
            assertThat(expectedNumber).isEqualTo(Integer.parseInt(captor.getValue()));

        }
    }

    @Test
    public void testOnClickCalculatorButtons_StartsHandling() {
        for(Button button : calculatorActivity.calculatorButtons) {
            ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
            String expectedOperator = button.getText().toString();
            button.performClick();
            Mockito.verify(calculatorActivity.presenter, Mockito.atLeastOnce()).handleCalculatorButtonPress(captor.capture());

            assertThat(expectedOperator).isEqualTo(captor.getValue());
        }
    }

    @Test
    public void testOnClickEqualsButton_StartsExpressionEvaluation() {
        ButterKnife.findById(calculatorActivity, R.id.eq).performClick();
        Mockito.verify(calculatorActivity.presenter).handleEvaluate();
    }

    @Test
    public void testOnClickDeleteButton_StartsHandlingDelete() {
        ButterKnife.findById(calculatorActivity, R.id.delete_op).performClick();
        Mockito.verify(calculatorActivity.presenter).handleDelete();
    }

}