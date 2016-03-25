package com.mobiquity.androidunittests.di.components;

import com.mobiquity.androidunittests.di.modules.AppModule;
import com.mobiquity.androidunittests.di.modules.DevModule;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.ViewWrapper;

import dagger.Component;

@AppScope
@Component(modules = {
        AppModule.class,
        DevModule.class
})
public interface AppComponent extends BaseComponent {

    // Provided by DevModule
    ViewWrapper viewWrapper();
}
