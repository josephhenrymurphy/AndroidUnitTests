package com.mobiquity.androidunittests.di.components;

import com.mobiquity.androidunittests.ui.fragment.DevSettingsFragment;

import dagger.Subcomponent;

@Subcomponent
public interface DevSettingsComponent {
    void inject(DevSettingsFragment fragment);
}
