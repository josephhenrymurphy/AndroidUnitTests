package com.mobiquity.androidunittests;

import android.os.Build;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

public class CustomGradleRunner extends RobolectricGradleTestRunner {

    private final static int MAX_SDK = Build.VERSION_CODES.LOLLIPOP;

    public CustomGradleRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public Config getConfig(Method method) {
        final Config defaultConfig = super.getConfig(method);

        return new Config.Implementation(
                new int[]{MAX_SDK},
                defaultConfig.manifest(),
                defaultConfig.qualifiers(),
                defaultConfig.packageName(),
                defaultConfig.resourceDir(),
                defaultConfig.assetDir(),
                defaultConfig.shadows(),
                CalculatorUnitTestApplication.class,
                defaultConfig.libraries(),
                defaultConfig.constants() == Void.class ? BuildConfig.class : defaultConfig.constants()
        );
    }
}
