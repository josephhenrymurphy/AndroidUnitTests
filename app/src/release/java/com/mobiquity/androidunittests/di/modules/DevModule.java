package com.mobiquity.androidunittests.di.modules;

import android.content.Context;

import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;
import com.mobiquity.androidunittests.devsettings.DevSettingsWrapper;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.ViewWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class DevModule {

    @AppScope
    @Provides
    ViewWrapper provideViewWrapper() {
        return view -> view;
    }

    @Provides
    @AppScope
    DevSettingsWrapper provideDevSettingsWrapper() {
        return () -> {};
    }

    @Provides
    @AppScope
    protected AndroidDevMetricsWrapper provideDevMetricsWrapper() {
        return context -> {};
    }
}
