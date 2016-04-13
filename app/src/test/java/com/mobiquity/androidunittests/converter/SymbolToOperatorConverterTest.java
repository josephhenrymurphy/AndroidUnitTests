package com.mobiquity.androidunittests.converter;

import com.mobiquity.androidunittests.CustomGradleRunner;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.operator.AdditionOperator;
import com.mobiquity.androidunittests.calculator.input.operator.MultiplicationOperator;
import com.mobiquity.androidunittests.calculator.input.operator.NoOpOperator;
import com.mobiquity.androidunittests.calculator.input.operator.Operator;
import com.mobiquity.androidunittests.calculator.input.operator.SubtractionOperator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.*;

@RunWith(CustomGradleRunner.class)
public class SymbolToOperatorConverterTest {

    private SymbolToOperatorConverter converter;

    @Before
    public void setUp() {
        converter = new SymbolToOperatorConverter(RuntimeEnvironment.application);
    }

    @Test
    public void testConverter_ConvertPlusToAddOperation() {
        String addSymbol = RuntimeEnvironment.application.getString(R.string.add_op);
        Operator operator = converter.convert(addSymbol);

        assertThat(operator).isInstanceOf(AdditionOperator.class);
    }

    @Test
    public void testConverter_ConvertMinusToSubtractOperation() {
        String subtractSymbol = RuntimeEnvironment.application.getString(R.string.substract_op);
        Operator operator = converter.convert(subtractSymbol);

        assertThat(operator).isInstanceOf(SubtractionOperator.class);
    }

    @Test
    public void testConverter_ConvertStarToMultiplyOperation() {
        String multiplySymbol = RuntimeEnvironment.application.getString(R.string.multiply_op);
        Operator operator = converter.convert(multiplySymbol);

        assertThat(operator).isInstanceOf(MultiplicationOperator.class);
    }

    @Test
    public void testConverter_ConvertUnknownSymbolToNoOp() {
        String unknownSymbol = "unknown_symbol";
        Operator operator = converter.convert(unknownSymbol);

        assertThat(operator).isInstanceOf(NoOpOperator.class);
    }


}