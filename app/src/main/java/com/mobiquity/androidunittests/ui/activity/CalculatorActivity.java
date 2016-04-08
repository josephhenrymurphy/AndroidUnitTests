package com.mobiquity.androidunittests.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.components.CalculatorComponent;
import com.mobiquity.androidunittests.di.components.DaggerCalculatorComponent;
import com.mobiquity.androidunittests.ui.ViewWrapper;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;
import com.mobiquity.androidunittests.ui.presenter.CalculatorPresenter;
import com.mobiquity.androidunittests.ui.view.NumericPad;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculatorActivity extends BaseActivity<CalculatorComponent>
        implements CalculatorView {

    private CalculatorComponent calculatorComponent;

    @Inject ViewWrapper viewWrapper;
    @Inject CalculatorPresenter presenter;

    @Bind(R.id.numeric_pad) NumericPad numericPad;
    @Bind(R.id.display_input) EditText displayInput;
    @Bind(R.id.display_result) TextView resultText;
    @Bind(R.id.sliding_panel) SlidingUpPanelLayout slidingPanel;

    @Bind(value = {
            R.id.add_op,
            R.id.subtract_op,
            R.id.multiply_op,
            R.id.divide_op
    }) List<Button> operatorButtons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculatorComponent = prepareComponent();
        calculatorComponent.inject(this);
        setContentView(viewWrapper.wrap(getLayoutInflater().inflate(R.layout.activity_calculator, null)));
        ButterKnife.bind(this);

        numericPad.addOnNumberClickedListener(number -> presenter.handleNumber(number));
        ButterKnife.apply(operatorButtons, (button, index) -> {
            String symbol = button.getText().toString();
            button.setOnClickListener(v -> presenter.handleOperator(symbol));
        });
    }

    @Override
    protected CalculatorComponent prepareComponent() {
        return DaggerCalculatorComponent.builder()
                .appComponent(CalculatorApplication.getAppComponent(this))
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @OnClick(R.id.eq)
    void onClickEqualsButton() {
        presenter.evaluate();
    }

    @OnClick(R.id.extra_button_wolfram)
    void onClickWolframButton() {
        slidingPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {}

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingPanel.removePanelSlideListener(this);
                    Intent intent = new Intent(CalculatorActivity.this, WolframActivity.class);
                    startActivity(intent);
                }
            }
        });
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void updateDisplayText(String displayText) {
        displayInput.setText(displayText);
    }

    @Override
    public void showSuccessfulCalculation(String result) {
        resultText.setText(result);
    }
}
