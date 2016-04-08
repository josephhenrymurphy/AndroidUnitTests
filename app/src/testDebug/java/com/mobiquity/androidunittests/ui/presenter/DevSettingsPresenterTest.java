package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.devsettings.DevSettingsWrapperImpl;
import com.mobiquity.androidunittests.ui.mvpview.DevView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class DevSettingsPresenterTest {

    private DevSettingsPresenter devSettingsPresenter;
    @Mock DevSettingsWrapperImpl devSettingsWrapper;
    @Mock DevView devView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        devSettingsPresenter = new DevSettingsPresenter(devSettingsWrapper);
    }

    @Test
    public void bindView_ShouldSendLeakCanaryStateToView() {
        Mockito.when(devSettingsWrapper.isLeakCanaryEnabled()).thenReturn(false);
        devSettingsPresenter.bind(devView);
        Mockito.verify(devView).changeLeakCanaryState(false);
    }

    @Test
    public void updateLeakCanaryState_ShouldEnableLeakCanaryAndNotifyView() {
        Mockito.when(devSettingsWrapper.isLeakCanaryEnabled()).thenReturn(false);

        devSettingsPresenter.bind(devView);
        devSettingsPresenter.updateLeakCanaryState(true);
        Mockito.verify(devSettingsWrapper).changeLeakCanaryState(true);
        Mockito.verify(devView).showMessage(Mockito.contains("enabled"));
        Mockito.verify(devView).showAppRestartMessage();
    }

    @Test
    public void updateLeakCanaryState_ShouldDisableLeakCanaryAndNotifyView() {
        Mockito.when(devSettingsWrapper.isLeakCanaryEnabled()).thenReturn(true);

        devSettingsPresenter.bind(devView);
        devSettingsPresenter.updateLeakCanaryState(false);
        Mockito.verify(devSettingsWrapper).changeLeakCanaryState(false);
        Mockito.verify(devView).showMessage(Mockito.contains("disabled"));
        Mockito.verify(devView).showAppRestartMessage();
    }

    @Test
    public void updateLeakCanaryState_ShouldNoOpIfStateIsSame() {

        // Leak canary is enabled
        devSettingsPresenter.bind(devView);
        Mockito.when(devSettingsWrapper.isLeakCanaryEnabled()).thenReturn(true);
        devSettingsPresenter.updateLeakCanaryState(true);
        devSettingsPresenter.unbind();

        // Leak canary is disabled
        devSettingsPresenter.bind(devView);
        Mockito.when(devSettingsWrapper.isLeakCanaryEnabled()).thenReturn(false);
        devSettingsPresenter.updateLeakCanaryState(false);
        devSettingsPresenter.unbind();

        // Verify NoOp
        Mockito.verify(devSettingsWrapper, Mockito.never()).changeLeakCanaryState(false);
        Mockito.verify(devView, Mockito.never()).showMessage(Mockito.anyString());
        Mockito.verify(devView, Mockito.never()).showAppRestartMessage();
    }


}