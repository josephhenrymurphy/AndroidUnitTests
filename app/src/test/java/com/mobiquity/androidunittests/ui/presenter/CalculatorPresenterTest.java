package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;
import com.mobiquity.androidunittests.converter.ExpressionConverter;
import com.mobiquity.androidunittests.converter.SymbolToOperatorConverter;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static com.google.common.truth.Truth.*;

import static com.mobiquity.androidunittests.testutil.InputSubject.*;
import static com.mobiquity.androidunittests.testutil.MockitoInvocationHelper.onlyLastInvocation;

public class CalculatorPresenterTest {

    @Mock Calculator calculator;
    @Mock ExpressionConverter expressionConverter;
    @Mock CalculatorView mockView;

    private CalculatorPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new CalculatorPresenter(calculator, expressionConverter);
    }

    @Test
    public void testHandleNumber_UpdatesDisplay() {
        Mockito.when(expressionConverter.normalize(Mockito.anyList())).thenReturn(
                Arrays.asList("3")
        );
        presenter.bind(mockView);
        presenter.handleNumber(3);

        Mockito.verify(mockView).updateDisplayText(Mockito.eq("3"));
    }

    @Test
    public void testHandleOperator_UpdatesDisplay() {
        Mockito.when(expressionConverter.normalize(Mockito.anyList())).thenReturn(
                Arrays.asList("-")
        );
        presenter.bind(mockView);
        presenter.handleOperator("-");

        Mockito.verify(mockView, onlyLastInvocation()).updateDisplayText(Mockito.eq("-"));
    }

    @Test
    public void testEvaluate_GivesCorrectInputToCalculator() {
        Mockito.when(expressionConverter.convert(Mockito.anyList())).thenReturn(
                Arrays.asList(new NumericInput(3),
                        new AdditionOperator(),
                        new NumericInput(4)
                )
        );
        presenter.bind(mockView);
        presenter.evaluate();
        ArgumentCaptor<Input[]> argumentCaptor = ArgumentCaptor.forClass(Input[].class);
        Mockito.verify(calculator).evaluate(argumentCaptor.capture());

        Input[] calculatorInput = argumentCaptor.getValue();
        assertThat(calculatorInput).hasLength(3);
        assertAbout(input()).that(calculatorInput[0]).valueEqualTo(new NumericInput(3));
        assertAbout(input()).that(calculatorInput[1]).valueEqualTo(new AdditionOperator());
        assertAbout(input()).that(calculatorInput[2]).valueEqualTo(new NumericInput(4));
    }

    @Test
    public void testEvaluate_OnEvaluateSuccess_DisplaysResult() {
        Mockito.when(calculator.evaluate(Mockito.any()))
                .thenReturn(3);

        presenter.bind(mockView);
        presenter.evaluate();
        Mockito.verify(mockView).showSuccessfulCalculation(Mockito.eq("3"));
    }

}