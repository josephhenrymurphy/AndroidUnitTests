package com.mobiquity.androidunittests;

import android.app.Application;
import android.content.Context;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.devsettings.DevSettingsWrapper;
import com.mobiquity.androidunittests.di.components.AppComponent;
import com.mobiquity.androidunittests.di.components.DaggerAppComponent;
import com.mobiquity.androidunittests.di.modules.AppModule;

import javax.inject.Inject;

import dagger.Lazy;
import timber.log.Timber;

public class CalculatorApplication extends Application {

    private AppComponent appComponent;
    @Inject Lazy<DevSettingsWrapper> devSettingsWrapper;
    @Inject Lazy<AndroidDevMetricsWrapper> androidDevMetricsWrapper;

    public static AppComponent getAppComponent(Context context) {
        return ((CalculatorApplication)context.getApplicationContext())
                .getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = prepareAppComponent().build();
        appComponent.inject(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            devSettingsWrapper.get().apply();
            androidDevMetricsWrapper.get().apply(this);
        }
    }

    protected DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this));
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
