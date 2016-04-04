package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.mvpview.MvpView;

import javax.inject.Inject;

@AppScope
public class CalculatorPresenter extends Presenter<MvpView> {

    @Inject
    public CalculatorPresenter() {

    }
}
