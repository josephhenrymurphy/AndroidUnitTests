package com.mobiquity.androidunittests.di.modules;

import android.app.Application;
import android.content.Context;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.calculator.Calculator;
import com.mobiquity.androidunittests.calculator.ExpressionConverter;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.net.services.WolframService;
import com.mobiquity.androidunittests.ui.presenter.CalculatorPresenter;
import com.mobiquity.androidunittests.ui.presenter.WolframPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(CalculatorApplication application) {
        this.application = application;
    }

    @Provides
    @AppScope
    Context provideContext() {
        return application;
    }

    @Provides
    @AppScope
    protected CalculatorPresenter provideCalculatorPresenter(Calculator calculator, ExpressionConverter converter) {
        return new CalculatorPresenter(calculator, converter);
    }

    @Provides
    @AppScope
    protected WolframPresenter provideWolframPresenter(WolframService wolframService) {
        return new WolframPresenter(wolframService);
    }

}
