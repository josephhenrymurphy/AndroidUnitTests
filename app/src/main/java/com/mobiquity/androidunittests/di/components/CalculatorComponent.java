package com.mobiquity.androidunittests.di.components;


import com.mobiquity.androidunittests.di.scopes.ActivityScope;
import com.mobiquity.androidunittests.ui.activity.CalculatorActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface CalculatorComponent extends BaseComponent {
    void inject(CalculatorActivity activity);
}
