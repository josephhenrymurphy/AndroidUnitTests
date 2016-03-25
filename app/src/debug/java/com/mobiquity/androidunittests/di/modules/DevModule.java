package com.mobiquity.androidunittests.di.modules;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.DevDrawerViewWrapper;
import com.mobiquity.androidunittests.ui.ViewWrapper;

import dagger.Module;
import dagger.Provides;

@Module
public class DevModule {

    @AppScope
    @Provides
    ViewWrapper provideViewModifier() {
        return new DevDrawerViewWrapper();
    }

}