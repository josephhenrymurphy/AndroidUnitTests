package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.NumericInput;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;
import com.mobiquity.androidunittests.converter.ExpressionConverter;
import com.mobiquity.androidunittests.converter.SymbolToOperatorConverter;
import com.mobiquity.androidunittests.testutil.MockitoInvocationHelper;
import com.mobiquity.androidunittests.testutil.ReflectionUtil;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.*;

import static com.mobiquity.androidunittests.testutil.InputSubject.*;
import static com.mobiquity.androidunittests.testutil.MockitoInvocationHelper.*;
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
    public void testHandleEvaluate_GivesCorrectInputToCalculator() throws Exception{
        Mockito.when(expressionConverter.convert(Mockito.anyList())).thenReturn(
                Arrays.asList(new NumericInput(3),
                        new AdditionOperator(),
                        new NumericInput(4)
                )
        );
        presenter.bind(mockView);
        presenter.handleEvaluate();
        ArgumentCaptor<Input[]> argumentCaptor = ArgumentCaptor.forClass(Input[].class);
        Mockito.verify(calculator, Mockito.atLeastOnce()).evaluate(argumentCaptor.capture());

        Input[] calculatorInput = argumentCaptor.getValue();
        assertThat(calculatorInput).hasLength(3);
        assertAbout(input()).that(calculatorInput[0]).valueEqualTo(new NumericInput(3));
        assertAbout(input()).that(calculatorInput[1]).valueEqualTo(new AdditionOperator());
        assertAbout(input()).that(calculatorInput[2]).valueEqualTo(new NumericInput(4));
    }

    @Test
    public void testHandleInput_ShowSuccessfulCalculation() throws Exception{
        // Have the calculator throw exception until a successful calculation
        Mockito.when(calculator.evaluate(Mockito.any())).thenThrow(
                new Calculator.CalculatorEvaluationException("Invalid Expression"));
        presenter.bind(mockView);

        Mockito.when(expressionConverter.convert(Mockito.any())).thenReturn(
                Arrays.asList(new NumericInput(3)));
        presenter.handleNumber(3);

        Mockito.when(expressionConverter.convert(Mockito.any())).thenReturn(
                Arrays.asList(new NumericInput(3), new AdditionOperator()));
        presenter.handleOperator("+");

        // Setup the calculator to return a valid result
        Mockito.reset(calculator);
        Mockito.when(calculator.evaluate(Mockito.any())).thenReturn(7);
        Mockito.when(expressionConverter.convert(Mockito.any())).thenReturn(
                Arrays.asList(new NumericInput(3), new AdditionOperator(), new NumericInput(4)));
        presenter.handleNumber(4);

        Mockito.verify(mockView).showSuccessfulCalculation(Mockito.eq("7"));
    }


}