package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.ui.mvpview.MvpView;

import java.lang.ref.WeakReference;

public abstract class Presenter<V extends MvpView> {

    private WeakReference<V> view;

    protected V view() {
        return view == null ? null : view.get();
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    public void bind(V view) {
        this.view = new WeakReference<V>(view);
    }

    public void unbind() {
        if(view != null) {
            view.clear();
            view = null;
        }
    }

}
