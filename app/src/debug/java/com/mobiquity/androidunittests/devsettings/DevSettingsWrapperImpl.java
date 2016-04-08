package com.mobiquity.androidunittests.devsettings;

public class DevSettingsWrapperImpl implements DevSettingsWrapper {

    private DevSettings devSettings;
    private LeakCanaryProxy leakCanaryProxy;
    private boolean hasLeakCanaryBeenSetOnce;

    public DevSettingsWrapperImpl(DevSettings devSettings, LeakCanaryProxy leakCanaryProxy) {
        this.devSettings = devSettings;
        this.leakCanaryProxy = leakCanaryProxy;
    }

    @Override
    public void apply() {
        configureLeakCanary(devSettings.isLeakCanaryEnabled());
    }

    public void changeLeakCanaryState(boolean state) {
        devSettings.setLeakCanary(state);
        apply();
    }
    public boolean isLeakCanaryEnabled() {
        return devSettings.isLeakCanaryEnabled();
    }

    private void configureLeakCanary(boolean enable) {
        if(!hasLeakCanaryBeenSetOnce) {
            if(enable) {
                leakCanaryProxy.init();
                hasLeakCanaryBeenSetOnce = true;
            }
        }
    }
}
