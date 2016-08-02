package com.mobiquity.androidunittests.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.testutil.TestFileUtil;

import org.assertj.android.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.simpleframework.xml.core.Persister;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class WolframPodAdapterTest {

    private WolframPodAdapter adapter;

    @Before
    public void setUp() {
        adapter = new WolframPodAdapter();
    }

    @Test
    public void testGetItemCount_ShouldBeZeroByDefault() {
        assertThat(adapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void testGetItemCount_ShouldReturnCorrectItemCountAfterSetData() {
        WolframResponse.Pod mockPod = Mockito.mock(WolframResponse.Pod.class);
        adapter.setData(Arrays.asList(mockPod, mockPod, mockPod));

        assertThat(adapter.getItemCount()).isEqualTo(3);
    }

    @Test
    public void testSetData_ShouldNotifyObservers() {
        RecyclerView.AdapterDataObserver observer = Mockito.mock(RecyclerView.AdapterDataObserver.class);
        adapter.registerAdapterDataObserver(observer);

        adapter.setData(Collections.emptyList());
        Mockito.verify(observer).onChanged();
    }

    @Test
    public void testBindView_ShouldBindItemsToViewHolder() throws Exception {
        WolframResponse.Pod mockPod = Mockito.mock(WolframResponse.Pod.class);
        List<WolframResponse.Pod> pods = Arrays.asList(mockPod, mockPod, mockPod);

        adapter.setData(pods);
        for(int i = 0; i < adapter.getItemCount(); i++) {
            WolframPodAdapter.ViewHolder viewHolder = Mockito.mock(WolframPodAdapter.ViewHolder.class);
            adapter.onBindViewHolder(viewHolder, i);
            Mockito.verify(viewHolder).bind(Mockito.any(WolframResponse.Pod.class));
        }
    }

    @Test
    public void testViewHolder_BindShouldSetTitle() throws Exception{
        WolframResponse.Pod pod = getMockPod();
        LinearLayout parent = new LinearLayout(RuntimeEnvironment.application);
        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        WolframPodAdapter.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
        viewHolder.bind(pod);

        Assertions.assertThat(viewHolder.podTitle).hasText(pod.getTitle());
    }

    @Test
    public void testViewHolder_BindShouldAddSubpods() throws Exception{
        WolframResponse.Pod pod = getMockPod();
        WolframResponse.Subpod subpod = pod.getSubpods().get(0);

        LinearLayout parent = new LinearLayout(RuntimeEnvironment.application);
        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WolframPodAdapter.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, 0);
        viewHolder.bind(pod);

        Assertions.assertThat(viewHolder.podContent).hasChildCount(1);

        View subpodView = viewHolder.podContent.getChildAt(0);
        assertThat(subpodView).isInstanceOf(TextView.class);
        Assertions.assertThat(((TextView)subpodView)).hasText(subpod.getText());
    }

    private WolframResponse.Pod getMockPod() throws Exception {
        String fileContent = TestFileUtil.readFile("mock/mock_pod.xml");
        return new Persister().read(WolframResponse.Pod.class, fileContent);
    }

}