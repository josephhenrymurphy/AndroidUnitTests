package com.mobiquity.androidunittests.di.components;

import com.mobiquity.androidunittests.di.scopes.ActivityScope;
import com.mobiquity.androidunittests.ui.activity.WolframActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface WolframComponent extends BaseComponent {
    void inject(WolframActivity activity);
}
