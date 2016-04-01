package com.mobiquity.androidunittests.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.ui.mvpview.DevView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class DevSettingsFragment extends Fragment implements DevView {


    @Bind(R.id.leak_canary_switch) Switch leakCanarySwitch;

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
        presenter.bind(this);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbind();
    }
}
