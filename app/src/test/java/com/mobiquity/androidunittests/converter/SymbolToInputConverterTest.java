package com.mobiquity.androidunittests.converter;

import com.mobiquity.androidunittests.CustomGradleRunner;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.calculator.input.Input;
import com.mobiquity.androidunittests.calculator.input.LeftParenInput;
import com.mobiquity.androidunittests.calculator.input.RightParenInput;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static com.google.common.truth.Truth.*;

@RunWith(CustomGradleRunner.class)
public class SymbolToInputConverterTest {

    private SymbolToInputConverter converter;

    @Before
    public void setUp() {
        converter = new SymbolToInputConverter(RuntimeEnvironment.application);
    }

    @Test
    public void testConverter_ConvertLeftParen() {
        String leftParenSymbol = RuntimeEnvironment.application.getString(R.string.left_paren);
        Input input = converter.convert(leftParenSymbol);

        assertThat(input).isInstanceOf(LeftParenInput.class);
    }

    @Test
    public void testConverter_ConvertRightParen() {
        String rightParenSymbol = RuntimeEnvironment.application.getString(R.string.right_paren);
        Input input = converter.convert(rightParenSymbol);

        assertThat(input).isInstanceOf(RightParenInput.class);
    }

    @Test
    public void testConvert_UnknownSymbolReturnsNull() {
        String unknownSymbol = "abcd";
        Input input = converter.convert(unknownSymbol);

        assertThat(input).isNull();
    }
}