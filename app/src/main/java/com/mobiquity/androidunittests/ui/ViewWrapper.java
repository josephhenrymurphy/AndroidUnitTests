package com.mobiquity.androidunittests.ui;

import android.view.View;

public interface ViewWrapper<T extends View> {
    T wrap(View view);
}
