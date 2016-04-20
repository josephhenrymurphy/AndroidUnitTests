package com.mobiquity.androidunittests.testutil;

import android.content.Context;

import com.frogermcs.androiddevmetrics.aspect.ActivityLifecycleAnalyzer;
import com.mobiquity.androidunittests.devsettings.AndroidDevMetricsWrapper;

public final class MockAndroidDevMetrics implements AndroidDevMetricsWrapper {

    public MockAndroidDevMetrics() {
        ActivityLifecycleAnalyzer.setEnabled(false);
    }

    @Override
    public void apply(Context context) {}
}
