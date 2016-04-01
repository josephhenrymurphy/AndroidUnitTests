package com.mobiquity.androidunittests.di.modules;
import android.content.Context;
import android.content.SharedPreferences;

import com.mobiquity.androidunittests.DevSettings;
import com.mobiquity.androidunittests.DevSettingsWrapperImpl;
import com.mobiquity.androidunittests.devsettings.DevSettingsWrapper;
import com.mobiquity.androidunittests.devsettings.LeakCanaryProxy;
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
    @Debug
    SharedPreferences provideDebugSharedPreferences(Context context) {
        return context.getSharedPreferences("dev_settings", Context.MODE_PRIVATE);
    }

    @Provides
    @AppScope
    DevSettingsWrapperImpl provideDevSettingsWrapperImpl(DevSettings devSettings, LeakCanaryProxy leakCanaryProxy) {
        return new DevSettingsWrapperImpl(devSettings, leakCanaryProxy);
    }

    @AppScope
    @Provides
    ViewWrapper provideViewModifier() {
        return new DevDrawerViewWrapper();
    }

    @Provides
    @AppScope
    DevSettingsWrapper provideDevSettingsWrapper(DevSettingsWrapperImpl devSettingsWrapper) {
        return devSettingsWrapper;
    }

}