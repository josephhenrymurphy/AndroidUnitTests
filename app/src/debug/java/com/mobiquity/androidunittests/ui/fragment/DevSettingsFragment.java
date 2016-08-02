package com.mobiquity.androidunittests.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiquity.androidunittests.BuildConfig;
import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.ui.mvpview.DevView;
import com.mobiquity.androidunittests.ui.presenter.DevSettingsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class DevSettingsFragment extends Fragment implements DevView {

    @BindView(R.id.build_flavor) TextView buildFlavorText;
    @BindView(R.id.build_version_code) TextView buildVersionCodeText;
    @BindView(R.id.build_version_name) TextView buildVersionNameText;
    @BindView(R.id.leak_canary_switch) Switch leakCanarySwitch;

    @Inject DevSettingsPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalculatorApplication.getAppComponent(getContext())
                .plusDevSettingsComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dev_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        buildFlavorText.setText(TextUtils.isEmpty(BuildConfig.FLAVOR) ? "N/A" : BuildConfig.FLAVOR);
        buildVersionCodeText.setText(String.valueOf(BuildConfig.VERSION_CODE));
        buildVersionNameText.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @OnCheckedChanged(R.id.leak_canary_switch)
    void onLeakCanarySwitchChanged(boolean checked) {
        presenter.updateLeakCanaryState(checked);
    }

    @Override
    public void changeLeakCanaryState(boolean enabled) {
        leakCanarySwitch.setChecked(enabled);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAppRestartMessage() {
        Toast.makeText(getContext(), R.string.app_restart_message, Toast.LENGTH_SHORT).show();
    }

}
