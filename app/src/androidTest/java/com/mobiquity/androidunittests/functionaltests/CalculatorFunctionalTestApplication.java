package com.mobiquity.androidunittests.functionaltests;

import android.content.Context;

import com.mobiquity.androidunittests.CalculatorApplication;

public class CalculatorFunctionalTestApplication extends CalculatorApplication {

    // Recreate application modules to clear application state for tests
    public static void clear(Context context) {
        CalculatorFunctionalTestApplication application = (CalculatorFunctionalTestApplication) context.getApplicationContext();
        application.appComponent = application.prepareAppComponent().build();
        application.appComponent.inject(application);
    }

}
