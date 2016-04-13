package com.mobiquity.androidunittests;

import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.di.components.DaggerAppComponent;
import com.mobiquity.androidunittests.di.modules.AppModule;

public class CalculatorUnitTestApplication extends CalculatorApplication {

    // No-op for AndroidDevMetrics for unit tests
    @Override
    protected DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this) {
                    @Override
                    protected AndroidDevMetricsWrapper provideAndroidDevMetricsWrapper() {
                        return context -> {};
                    }
                });
    }

}
