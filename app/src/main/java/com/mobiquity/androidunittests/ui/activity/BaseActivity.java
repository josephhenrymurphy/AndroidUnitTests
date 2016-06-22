package com.mobiquity.androidunittests.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobiquity.androidunittests.di.components.BaseComponent;
import com.mobiquity.androidunittests.ui.ViewWrapper;

import javax.inject.Inject;

public abstract class BaseActivity<C extends BaseComponent> extends AppCompatActivity {

    @Inject ViewWrapper viewWrapper;

    abstract protected C getComponent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent();
    }

    protected void setContentView(@LayoutRes int layoutResID, boolean showDevDrawer) {
        if(showDevDrawer) {
            setContentView(viewWrapper.wrap(getLayoutInflater().inflate(layoutResID, null)));
        } else {
            super.setContentView(layoutResID);
        }
    }
}
