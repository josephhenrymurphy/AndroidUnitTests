package com.mobiquity.androidunittests;

import android.app.Application;
import android.content.Context;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.mobiquity.androidunittests.di.components.AppComponent;
import com.mobiquity.androidunittests.di.components.DaggerAppComponent;
import com.mobiquity.androidunittests.di.modules.AppModule;

import timber.log.Timber;

public class CalculatorApplication extends Application {

    private AppComponent appComponent;

    public static AppComponent getAppComponent(Context context) {
        return ((CalculatorApplication)context.getApplicationContext())
                .getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = prepareAppComponent().build();
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            AndroidDevMetrics.initWith(this);
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
