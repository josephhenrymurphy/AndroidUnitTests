package com.mobiquity.androidunittests.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.components.CalculatorComponent;
import com.mobiquity.androidunittests.di.components.DaggerCalculatorComponent;
import com.mobiquity.androidunittests.ui.ViewWrapper;

import javax.inject.Inject;

public class CalculatorActivity extends BaseActivity<CalculatorComponent> {

    private CalculatorComponent calculatorComponent;

    @Inject ViewWrapper viewWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculatorComponent = prepareComponent();
        calculatorComponent.inject(this);
        setContentView(viewWrapper.wrap(getLayoutInflater().inflate(R.layout.activity_calculator, null)));
    }

    @Override
    protected CalculatorComponent prepareComponent() {
        return DaggerCalculatorComponent.builder()
                .appComponent(CalculatorApplication.getAppComponent(this))
                .build();
    }
}
