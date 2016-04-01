package com.mobiquity.androidunittests.devsettings;

import android.content.SharedPreferences;

import com.mobiquity.androidunittests.di.qualifiers.Debug;
import com.mobiquity.androidunittests.di.scopes.AppScope;

import javax.inject.Inject;

@AppScope
public class DevSettings {
    private static final String LEAK_CANARY = "leak_canary";

    private final SharedPreferences sharedPreferences;

    @Inject
    public DevSettings(@Debug SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isLeakCanaryEnabled() {
        return sharedPreferences.getBoolean(LEAK_CANARY, false);
    }

    public void setLeakCanary(boolean value) {
        sharedPreferences.edit().putBoolean(LEAK_CANARY, value).apply();
    }


}
