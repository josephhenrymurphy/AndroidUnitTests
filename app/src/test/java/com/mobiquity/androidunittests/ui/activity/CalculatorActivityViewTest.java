package com.mobiquity.androidunittests.ui.activity;

import com.mobiquity.androidunittests.CustomGradleRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static com.google.common.truth.Truth.*;

@RunWith(CustomGradleRunner.class)
public class CalculatorActivityViewTest {

    private CalculatorActivity calculatorActivity;

    @Before
    public void setUp() {
        calculatorActivity = Robolectric.setupActivity(CalculatorActivity.class);
    }

    @Test
    public void testUpdateDisplay_ChangesDisplayText() {
        String expectedDisplayText = "1+1";
        calculatorActivity.updateDisplayText(expectedDisplayText);

        assertThat(calculatorActivity.displayInput.getText().toString())
                .isEqualTo(expectedDisplayText);
    }

    @Test
    public void testShowSuccessfulCalculation_ChangesResultText() {
        String expectedResultText = "12";
        calculatorActivity.showSuccessfulCalculation(expectedResultText);

        assertThat(calculatorActivity.resultText.getText().toString())
                .isEqualTo(expectedResultText);
    }
}