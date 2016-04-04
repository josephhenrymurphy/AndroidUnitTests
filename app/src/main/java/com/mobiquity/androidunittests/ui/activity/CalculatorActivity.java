package com.mobiquity.androidunittests.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.components.CalculatorComponent;
import com.mobiquity.androidunittests.di.components.DaggerCalculatorComponent;
import com.mobiquity.androidunittests.ui.ViewWrapper;
import com.mobiquity.androidunittests.ui.mvpview.CalculatorView;
import com.mobiquity.androidunittests.ui.presenter.CalculatorPresenter;
import com.mobiquity.androidunittests.ui.view.NumericPad;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalculatorActivity extends BaseActivity<CalculatorComponent>
        implements CalculatorView {

    private CalculatorComponent calculatorComponent;

    @Inject ViewWrapper viewWrapper;
    @Inject CalculatorPresenter presenter;
    @Bind(R.id.numeric_pad) NumericPad numericPad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculatorComponent = prepareComponent();
        calculatorComponent.inject(this);
        setContentView(viewWrapper.wrap(getLayoutInflater().inflate(R.layout.activity_calculator, null)));
        ButterKnife.bind(this);
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

}
