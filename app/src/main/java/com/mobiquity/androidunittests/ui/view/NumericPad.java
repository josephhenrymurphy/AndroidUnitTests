package com.mobiquity.androidunittests.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mobiquity.androidunittests.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NumericPad extends FrameLayout {

    @Bind(value = {
            R.id.digit_0, R.id.digit_1, R.id.digit_2,
            R.id.digit_3, R.id.digit_4, R.id.digit_5,
            R.id.digit_6, R.id.digit_7, R.id.digit_8,
            R.id.digit_9
    }) List<Button> numericButtons;

    public NumericPad(Context context) {
        this(context, null);
    }

    public NumericPad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumericPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.addView(inflater.inflate(R.layout.numeric_pad, null));
        ButterKnife.bind(this, this);
        ButterKnife.apply(numericButtons, (button, index) -> button.setText(String.valueOf(index)));
    }
}
