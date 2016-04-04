package com.mobiquity.androidunittests.devsettings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.google.common.truth.Truth.*;

public class DevSettingsWrapperImplTest {

    private DevSettingsWrapperImpl devSettingsWrapper;
    @Mock DevSettings devSettings;
    @Mock LeakCanaryProxy leakCanaryProxy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        devSettingsWrapper = new DevSettingsWrapperImpl(devSettings, leakCanaryProxy);
    }

    @Test
    public void testIsLeakCanaryEnabled_ShouldReturnValueFromDevSettings() {
        Mockito.when(devSettings.isLeakCanaryEnabled()).thenReturn(true);
        assertThat(devSettingsWrapper.isLeakCanaryEnabled()).isTrue();
        Mockito.verify(devSettings).isLeakCanaryEnabled();

        Mockito.when(devSettings.isLeakCanaryEnabled()).thenReturn(false);
        assertThat(devSettingsWrapper.isLeakCanaryEnabled()).isFalse();
        Mockito.verify(devSettings, Mockito.times(2)).isLeakCanaryEnabled();
    }

    @Test
    public void testChangeLeakCanaryState_ShouldEitDevSettings() {
        devSettingsWrapper.changeLeakCanaryState(true);
        Mockito.verify(devSettings).setLeakCanary(Mockito.anyBoolean());
    }

    @Test
    public void testChangeLeakCanaryState_EnableLeakCanary() {
        Mockito.when(devSettings.isLeakCanaryEnabled()).thenReturn(true);
        devSettingsWrapper.changeLeakCanaryState(true);
        Mockito.verify(leakCanaryProxy).init();
    }

    @Test
    public void testChangeLeakCanaryState_CannotEnableLeakCanaryMultipleTimes() {
        Mockito.when(devSettings.isLeakCanaryEnabled()).thenReturn(true);
        devSettingsWrapper.changeLeakCanaryState(true);
        devSettingsWrapper.changeLeakCanaryState(true);
        Mockito.verify(leakCanaryProxy, Mockito.only()).init();
    }

}