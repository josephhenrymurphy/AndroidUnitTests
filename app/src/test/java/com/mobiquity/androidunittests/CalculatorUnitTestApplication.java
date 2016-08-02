package com.mobiquity.androidunittests;

import com.frogermcs.androiddevmetrics.aspect.ActivityLifecycleAnalyzer;
import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.ExpressionConverter;
import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.di.components.DaggerAppComponent;
import com.mobiquity.androidunittests.di.modules.AppModule;
import com.mobiquity.androidunittests.di.modules.DevModule;
import com.mobiquity.androidunittests.net.services.WolframService;
import com.mobiquity.androidunittests.ui.presenter.CalculatorPresenter;
import com.mobiquity.androidunittests.ui.presenter.WolframPresenter;

import org.mockito.Mockito;

public class CalculatorUnitTestApplication extends CalculatorApplication {
    
    @Override
    protected DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this) {
                    @Override
                    protected CalculatorPresenter provideCalculatorPresenter(Calculator calculator, ExpressionConverter converter) {
                        return Mockito.mock(CalculatorPresenter.class);
                    }

                    @Override
                    protected WolframPresenter provideWolframPresenter(WolframService wolframService) {
                        return Mockito.mock(WolframPresenter.class);
                    }
                })
                .devModule(new DevModule() {
                    @Override
                    protected AndroidDevMetricsWrapper provideAndroidDevMetrics() {
                        ActivityLifecycleAnalyzer.setEnabled(false);
                        return context -> {};
                    }
                });
    }
}
