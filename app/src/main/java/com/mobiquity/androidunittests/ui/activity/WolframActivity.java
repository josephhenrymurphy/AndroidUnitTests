package com.mobiquity.androidunittests.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobiquity.androidunittests.CalculatorApplication;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.di.components.DaggerWolframComponent;
import com.mobiquity.androidunittests.di.components.WolframComponent;
import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.ui.ViewWrapper;
import com.mobiquity.androidunittests.ui.adapter.WolframPodAdapter;
import com.mobiquity.androidunittests.ui.mvpview.WolframView;
import com.mobiquity.androidunittests.ui.presenter.WolframPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class WolframActivity extends BaseActivity<WolframComponent>
    implements WolframView {

    @Bind(R.id.wolfram_input) EditText queryInput;
    @Bind(R.id.wolfram_submit) ImageButton wolframSubmitButton;
    @Bind(R.id.wolfram_pod_list) RecyclerView podList;

    @Inject WolframPresenter presenter;
    WolframPodAdapter wolframAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component.inject(this);
        setContentView(R.layout.activity_wolfram, true);
        ButterKnife.bind(this);

        podList.setLayoutManager(new LinearLayoutManager(this));
        wolframAdapter = new WolframPodAdapter();
        podList.setAdapter(wolframAdapter);
    }

    @Override
    protected WolframComponent buildComponent() {
        return DaggerWolframComponent.builder()
                    .appComponent(CalculatorApplication.getAppComponent(this))
                    .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
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
        presenter.startQuery(query);
    }
}
