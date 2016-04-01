package com.mobiquity.androidunittests.di.modules;

import android.app.Application;
import android.content.Context;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.devsettings.DevSettingsWrapper;
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

}
