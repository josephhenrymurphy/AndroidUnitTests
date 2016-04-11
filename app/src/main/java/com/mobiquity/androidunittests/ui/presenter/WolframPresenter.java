package com.mobiquity.androidunittests.ui.presenter;

import com.mobiquity.androidunittests.di.scopes.AppScope;
import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.net.services.WolframService;
import com.mobiquity.androidunittests.ui.mvpview.WolframView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@AppScope
public class WolframPresenter extends Presenter<WolframView> {

    private WolframService wolframService;

    @Inject
    public WolframPresenter(WolframService wolframService) {
        this.wolframService = wolframService;
    }

    public void startQuery(String query) {
        wolframService.query(query).enqueue(new Callback<WolframResponse>() {
            @Override
            public void onResponse(Call<WolframResponse> call, Response<WolframResponse> response) {
                view().updatePods(response.body().getPods());
            }

            @Override
            public void onFailure(Call<WolframResponse> call, Throwable t) {
                Timber.e(t, t.getMessage());
                view().showWolframFailure();

            }
        });
    }
}
