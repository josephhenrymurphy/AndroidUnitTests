package com.mobiquity.androidunittests.di.modules;

import android.app.Application;

import com.mobiquity.androidunittests.CalculatorApplication;

import dagger.Module;

@Module
public class AppModule {

    private final Application application;

    public AppModule(CalculatorApplication application) {
        this.application = application;
    }


}
