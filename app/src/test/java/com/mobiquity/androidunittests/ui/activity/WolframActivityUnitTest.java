package com.mobiquity.androidunittests.ui.activity;

import android.view.inputmethod.EditorInfo;

import com.mobiquity.androidunittests.CustomGradleRunner;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.testutil.TestFileUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import butterknife.ButterKnife;

import static com.google.common.truth.Truth.assertThat;

@RunWith(CustomGradleRunner.class)
public class WolframActivityUnitTest {

    private WolframActivity wolframActivity;
    private ActivityController<WolframActivity> activityController;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(WolframActivity.class).setup();
        wolframActivity = activityController.get();
    }

    @Test
    public void testPresenterBound_OnResume() {
        Mockito.verify(wolframActivity.presenter).bind(wolframActivity);
    }

    @Test
    public void testPresenterUnbound_OnPause() {
        activityController.pause();
        Mockito.verify(wolframActivity.presenter).unbind();
    }

    @Test
    public void testClickQueryDone_StartsQueryWithInput() {
        wolframActivity.queryInput.setText("pi");
        ButterKnife.findById(wolframActivity, R.id.wolfram_submit).performClick();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(wolframActivity.presenter).startQuery(captor.capture());
        assertThat(captor.getValue()).isEqualTo("pi");
    }

    @Test
    public void testClickEnter_StartsQueryWithInput() {
        wolframActivity.queryInput.setText("pi");
        wolframActivity.queryInput.onEditorAction(EditorInfo.IME_ACTION_DONE);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(wolframActivity.presenter).startQuery(captor.capture());
        assertThat(captor.getValue()).isEqualTo("pi");
    }

    @Test
    public void testShowWolframFailure() {
        wolframActivity.showWolframFailure();
        String latestToast = ShadowToast.getTextOfLatestToast();

        assertThat(latestToast).isEqualTo(wolframActivity.getString(R.string.wolfram_failure));
    }

    @Test
    public void testUpdatePods() throws Exception {
        assertThat(wolframActivity.wolframAdapter.getItemCount()).isEqualTo(0);

        List<WolframResponse.Pod> pods = getMockPods();
        wolframActivity.updatePods(pods);
        assertThat(wolframActivity.wolframAdapter.getItemCount()).isEqualTo(pods.size());
    }

    private List<WolframResponse.Pod> getMockPods() throws Exception {
        String fileContent = TestFileUtil.readFile("mock/wolfram_pi_response.xml");
        WolframResponse response = new Persister().read(WolframResponse.class, fileContent);
        return response.getPods();
    }

}