package com.mobiquity.androidunittests.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.components.BaseComponent;

import butterknife.ButterKnife;

public class WolframActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wolfram);
        ButterKnife.bind(this);
    }

    @Override
    protected BaseComponent prepareComponent() {
        return null;
    }


}
