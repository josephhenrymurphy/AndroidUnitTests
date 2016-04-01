package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.devsettings.DevSettingsWrapperImpl;
import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.ui.mvpview.DevView;

import javax.inject.Inject;

@AppScope
public class DevSettingsPresenter extends Presenter<DevView> {

    private DevSettingsWrapperImpl devSettingsWrapper;

    @Inject
    public DevSettingsPresenter(DevSettingsWrapperImpl devSettingsWrapper) {
        this.devSettingsWrapper = devSettingsWrapper;
    }

    @Override
    public void bind(DevView view) {
        super.bind(view);
        view.changeLeakCanaryState(devSettingsWrapper.isLeakCanaryEnabled());
    }

    public void updateLeakCanaryState(boolean enabled) {
        if (devSettingsWrapper.isLeakCanaryEnabled() == enabled)
            return;

        devSettingsWrapper.changeLeakCanaryState(enabled);
        if (isViewAttached()) {
            String message = "Leak canary was " + (enabled ? "enabled" : "disabled") + ".";
            view().showMessage(message);
            view().showAppRestartMessage();
        }
    }

}
