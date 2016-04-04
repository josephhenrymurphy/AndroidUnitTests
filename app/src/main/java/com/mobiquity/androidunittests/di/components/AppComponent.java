package com.mobiquity.androidunittests.di.components;

import android.content.Context;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.di.modules.AppModule;
import com.mobiquity.androidunittests.di.modules.DevModule;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.ViewWrapper;
import com.mobiquity.androidunittests.ui.presenter.CalculatorPresenter;

import dagger.Component;

@AppScope
@Component(modules = {
        AppModule.class,
        DevModule.class
})
public interface AppComponent extends BaseComponent {

    void inject(CalculatorApplication application);
    DevSettingsComponent plusDevSettingsComponent();

    Context context();
    CalculatorPresenter calculatorPresenter();

    // Provided by DevModule
    ViewWrapper viewWrapper();

}
