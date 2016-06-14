package com.mobiquity.androidunittests.devsettings;

import android.app.Application;
import android.content.Context;

import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

@AppScope
public class LeakCanaryProxy {

    private final Application application;
    private RefWatcher refWatcher;

    @Inject
    public LeakCanaryProxy(Context context) {
        this.application = (Application) context.getApplicationContext();
    }

    public void init() {
        refWatcher = LeakCanary.install(application);
    }

    public void watch(Object watchedReference) {
        if(refWatcher != null) {
            refWatcher.watch(watchedReference);
        }
    }


}
