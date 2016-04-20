package com.mobiquity.androidunittests;

import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.converter.ExpressionConverter;
import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.di.components.DaggerAppComponent;
import com.mobiquity.androidunittests.di.modules.AppModule;
import com.mobiquity.androidunittests.testutil.MockAndroidDevMetrics;
import com.mobiquity.androidunittests.ui.presenter.CalculatorPresenter;

import org.mockito.Mockito;

public class CalculatorUnitTestApplication extends CalculatorApplication {

    // No-op for AndroidDevMetrics for unit tests

    @Override
    protected DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this) {
                    @Override
                    protected AndroidDevMetricsWrapper provideAndroidDevMetricsWrapper() {
                        return new MockAndroidDevMetrics();
                    }

                    @Override
                    protected CalculatorPresenter provideCalculatorPresenter(Calculator calculator, ExpressionConverter converter) {
                        return Mockito.mock(CalculatorPresenter.class);
                    }
                });
    }

}
