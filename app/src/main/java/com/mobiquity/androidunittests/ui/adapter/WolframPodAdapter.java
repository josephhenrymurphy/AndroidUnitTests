package com.mobiquity.androidunittests.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.net.models.WolframResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WolframPodAdapter extends RecyclerView.Adapter<WolframPodAdapter.ViewHolder> {

    private List<WolframResponse.Pod> pods;

    public WolframPodAdapter() {
        pods = Collections.emptyList();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.wolfram_pod_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WolframResponse.Pod pod = pods.get(position);
        holder.bind(pod);
    }

    @Override
    public int getItemCount() {
        return pods.size();
    }

    public void setData(List<WolframResponse.Pod> pods) {
        this.pods = Collections.unmodifiableList(pods);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutInflater inflater;
        @Bind(R.id.pod_title) TextView podTitle;
        @Bind(R.id.pod_content) LinearLayout podContent;

        public ViewHolder(View itemView) {
            super(itemView);
            inflater = LayoutInflater.from(itemView.getContext());
            ButterKnife.bind(this, itemView);
        }

        public void bind(WolframResponse.Pod pod) {
            podTitle.setText(pod.getTitle());
            podContent.removeAllViews();
            for(WolframResponse.Subpod subpod : pod.getSubpods()) {
                TextView subpodView = (TextView) inflater.inflate(R.layout.wolfram_subpod_text, null);
                subpodView.setText(subpod.getText());
                podContent.addView(subpodView);
            }
        }
    }
}
