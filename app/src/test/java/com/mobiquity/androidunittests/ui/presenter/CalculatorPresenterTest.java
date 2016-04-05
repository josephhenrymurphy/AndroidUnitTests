package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;
import com.mobiquity.androidunittests.converter.SymbolToOperatorConverter;
import com.mobiquity.androidunittests.testutil.MockitoInvocationHelper;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.mobiquity.androidunittests.testutil.MockitoInvocationHelper.onlyLastInvocation;

public class CalculatorPresenterTest {

    @Mock Calculator calculator;
    @Mock SymbolToOperatorConverter operatorConverter;
    @Mock CalculatorView mockView;

    private CalculatorPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new CalculatorPresenter(calculator, operatorConverter);
    }

    @Test
    public void testHandleNumber_NoPriorInputs_UpdatesDisplayWithCorrectText() {
        presenter.bind(mockView);
        presenter.handleNumber(3);

        Mockito.verify(mockView).updateDisplayText(Mockito.eq("3"));
    }

    @Test
    public void testHandleNumber_WithPriorInputs_UpdatesDisplayWithCorrectText() {
        presenter.bind(mockView);
        presenter.handleNumber(3);
        presenter.handleNumber(2);

        Mockito.verify(mockView, onlyLastInvocation()).updateDisplayText(Mockito.eq("32"));
    }

    @Test
    public void testHandleOperator_NoPriorOperator_UpdatesDisplayWithCorrectText() {
        Mockito.when(operatorConverter.convert(Mockito.anyString()))
                .thenReturn(new AdditionOperator());

        presenter.bind(mockView);
        presenter.handleNumber(3);
        presenter.handleOperator(Mockito.anyString());

        Mockito.verify(mockView, onlyLastInvocation()).updateDisplayText(Mockito.eq("3+"));
    }

    @Test
    public void testHandleOperator_WithPriorOperator_UpdatesDisplayTextWithCorrectText() {
        Mockito.when(operatorConverter.convert("+"))
                .thenReturn(new AdditionOperator());

        Mockito.when(operatorConverter.convert("-"))
                .thenReturn(new SubtractionOperator());


        presenter.bind(mockView);
        presenter.handleNumber(3);
        presenter.handleOperator("+");
        Mockito.verify(mockView).updateDisplayText(Mockito.eq("3+"));

        presenter.handleOperator("-");
        Mockito.verify(mockView).updateDisplayText(Mockito.eq("3-"));
    }

}