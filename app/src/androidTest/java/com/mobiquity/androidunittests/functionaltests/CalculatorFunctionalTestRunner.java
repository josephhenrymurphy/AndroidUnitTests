package com.mobiquity.androidunittests.functionaltests;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

public class CalculatorFunctionalTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return Instrumentation.newApplication(CalculatorFunctionalTestApplication.class, context);
    }
}
