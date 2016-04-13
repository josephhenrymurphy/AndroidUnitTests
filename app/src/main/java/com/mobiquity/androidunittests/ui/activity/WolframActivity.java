package com.mobiquity.androidunittests.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.components.BaseComponent;
import com.mobiquity.androidunittests.di.components.DaggerWolframComponent;
import com.mobiquity.androidunittests.di.components.WolframComponent;
import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.net.services.WolframService;
import com.mobiquity.androidunittests.ui.adapter.WolframPodAdapter;
import com.mobiquity.androidunittests.ui.mvpview.WolframView;
import com.mobiquity.androidunittests.ui.presenter.WolframPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class WolframActivity extends BaseActivity<WolframComponent>
    implements WolframView {

    private WolframComponent wolframComponent;

    @Bind(R.id.wolfram_input) EditText queryInput;
    @Bind(R.id.wolfram_submit) ImageButton wolframSubmitButton;
    @Bind(R.id.wolfram_pod_list) RecyclerView podList;

    @Inject WolframPresenter wolframPresenter;
    WolframPodAdapter wolframAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wolframComponent = prepareComponent();
        wolframComponent.inject(this);
        setContentView(R.layout.activity_wolfram);
        ButterKnife.bind(this);

        podList.setLayoutManager(new LinearLayoutManager(this));
        wolframAdapter = new WolframPodAdapter();
        podList.setAdapter(wolframAdapter);
    }

    @Override
    protected WolframComponent prepareComponent() {
        return DaggerWolframComponent.builder()
                .appComponent(CalculatorApplication.getAppComponent(this))
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wolframPresenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wolframPresenter.unbind();
    }

    @OnEditorAction(R.id.wolfram_input)
    boolean onQueryDoneClicked() {
        startQuery();
        return false;
    }

    @OnClick(R.id.wolfram_submit)
    void onClickWolframSubmitButon() {
        startQuery();
    }

    @Override
    public void updatePods(List<WolframResponse.Pod> pods) {
        wolframAdapter.setData(pods);
    }

    @Override
    public void showWolframFailure() {
        Toast.makeText(this, getString(R.string.wolfram_failure), Toast.LENGTH_SHORT).show();
    }

    private void startQuery() {
        String query = queryInput.getText().toString();
        wolframPresenter.startQuery(query);
    }
}
