package com.mobiquity.androidunittests.di.modules;
import android.content.Context;
import android.content.SharedPreferences;

import com.mobiquity.androidunittests.di.qualifiers.Debug;
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

    @AppScope
    @Provides
    @Debug
    SharedPreferences provideDebugSharedPreferences(Context context) {
        return context.getSharedPreferences("dev_settings", Context.MODE_PRIVATE);
    }

}