package com.mobiquity.androidunittests.di.modules;

import android.app.Application;
import android.content.Context;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.di.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(CalculatorApplication application) {
        this.application = application;
    }

    @Provides
    @AppScope
    Context provideContext() {
        return application;
    }

    @Provides
    @AppScope
    protected AndroidDevMetricsWrapper provideAndroidDevMetricsWrapper() {
        return AndroidDevMetrics::initWith;
    }

}
