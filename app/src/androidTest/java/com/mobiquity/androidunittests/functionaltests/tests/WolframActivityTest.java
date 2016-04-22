package com.mobiquity.androidunittests.functionaltests.tests;

import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.functionaltests.CalculatorFunctionalTestApplication;
import com.mobiquity.androidunittests.functionaltests.espresso.RecyclerViewInteraction;
import com.mobiquity.androidunittests.functionaltests.rules.DisableAnimationsRule;
import com.mobiquity.androidunittests.functionaltests.testutil.TestFileUtil;
import com.mobiquity.androidunittests.net.models.WolframResponse;
import com.mobiquity.androidunittests.ui.activity.WolframActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.simpleframework.xml.core.Persister;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.RootMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.mobiquity.androidunittests.functionaltests.espresso.RecyclerViewAssertions.recyclerViewShouldHaveItemsCount;
import static com.mobiquity.androidunittests.functionaltests.espresso.ToastMatcher.isToast;
import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
public class WolframActivityTest {

    @Rule
    public ActivityTestRule<WolframActivity> activityRule = new ActivityTestRule<>(WolframActivity.class);

    @Rule
    public DisableAnimationsRule disableAnimationsRule = new DisableAnimationsRule();

    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        CalculatorFunctionalTestApplication.setMockWebServer(activityRule.getActivity(), mockWebServer);
        mockWebServer.start();
    }

    @Test
    public void shouldDisplayWolframPodsFromSuccessfulResponse() throws Exception {
        String mockBody = getMockResponse();
        mockWebServer.enqueue(new MockResponse().setBody(mockBody));
        WolframResponse mockResponse = new Persister().read(WolframResponse.class, mockBody);

        onView(withId(R.id.wolfram_input)).perform(typeText("pi"));
        onView(withId(R.id.wolfram_submit)).perform(click());

        RecyclerViewInteraction.<WolframResponse.Pod>onRecyclerView(withId(R.id.wolfram_pod_list))
                .withItems(mockResponse.getPods())
                .checkAllItems((item, view, noViewException) -> {
                    // Check the title of the pod is correct
                    matches(hasDescendant(Matchers.allOf(
                            withId(R.id.pod_title),
                            withText(item.getTitle())
                    )));

                    // Check the subpods are displayed correctly
                    for(WolframResponse.Subpod subpod : item.getSubpods()) {
                        matches(hasDescendant(Matchers.allOf(
                                withId(R.id.pod_content),
                                hasSibling(withText(subpod.getText()))
                        )));
                    }
                });

        onView(withId(R.id.wolfram_pod_list)).check(recyclerViewShouldHaveItemsCount(mockResponse.getPods().size()));
    }

    @Test
    public void shouldDisplayWolframFailure() throws Exception {
        String mockBody = getMockFailureResponse();
        mockWebServer.enqueue(new MockResponse().setBody(mockBody));

        onView(withId(R.id.wolfram_input)).perform(typeText("this should be a failure"));
        onView(withId(R.id.wolfram_submit)).perform(click());

         onView(withText(R.string.wolfram_failure)).inRoot(isToast())
                .check(matches(isDisplayed()));
    }

    private String getMockResponse() throws Exception {
        return TestFileUtil.readFile("mock/mock_wolfram_success_response.xml");
    }

    private String getMockFailureResponse() throws Exception {
        return TestFileUtil.readFile("mock/mock_wolfram_failure.xml");
    }

    @After
    public void tearDown() throws Exception{
        mockWebServer.shutdown();
        CalculatorFunctionalTestApplication.setMockWebServer(activityRule.getActivity(), null);
    }

}
