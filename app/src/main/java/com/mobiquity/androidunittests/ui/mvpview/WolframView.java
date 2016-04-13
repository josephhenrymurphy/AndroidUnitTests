package com.mobiquity.androidunittests.ui.mvpview;

import com.mobiquity.androidunittests.net.models.WolframResponse;

import java.util.List;

public interface WolframView extends MvpView {
    void updatePods(List<WolframResponse.Pod> pods);
    void showWolframFailure();
}
