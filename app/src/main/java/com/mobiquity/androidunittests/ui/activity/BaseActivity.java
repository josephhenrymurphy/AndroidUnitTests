package com.mobiquity.androidunittests.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.mobiquity.androidunittests.di.components.BaseComponent;

public abstract class BaseActivity<T extends BaseComponent> extends AppCompatActivity {

    abstract protected T prepareComponent();
}
