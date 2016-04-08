package com.mobiquity.androidunittests.ui.mvpview;

public interface DevView extends MvpView {
    void changeLeakCanaryState(boolean enabled);

    void showAppRestartMessage();
    void showMessage(String message);
}
