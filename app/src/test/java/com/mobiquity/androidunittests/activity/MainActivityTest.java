package com.mobiquity.androidunittests.activity;

import android.util.Pair;

import com.mobiquity.androidunittests.BuildConfig;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of the MainActivity.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.mobiquity.androidunittests")
public class MainActivityTest {
    /** ActivityController for Activity under test. */
    private ActivityController<MainActivity> controller;
    /** Activity under test. */
    private MainActivity unit;

    /*
     * Here I used a pattern where I define the test as a set of test data and expected results.
     * This setup will allow new inputs to be added to a test with out having to change the code.
     * Anytime you can set up a test this way it will help the maintainability of the test code.
     */
    /** Set of test data for the addition test. */
    private List<Pair<Float, Float>> additionTestData = new ArrayList<>();
    /** Set of expected results for addition test. */
    private List<Float> additionExpectedResult = new ArrayList<>();
    {
        additionTestData.add(new Pair<>(0f, 10f));
        additionExpectedResult.add(10f);
        additionTestData.add(new Pair<>(0f, Float.NaN));
        additionExpectedResult.add(Float.NaN);
        additionTestData.add(new Pair<>(Float.NaN, 0f));
        additionExpectedResult.add(Float.NaN);
        additionTestData.add(new Pair<>(Float.NaN, Float.NaN));
        additionExpectedResult.add(Float.NaN);
        additionTestData.add(new Pair<>(10f, -10f));
        additionExpectedResult.add(0f);
        additionTestData.add(new Pair<>(20f, -10f));
        additionExpectedResult.add(10f);
    }

    @Before
    public void setup() {
        controller = Robolectric.buildActivity(MainActivity.class);
        unit = controller.create().start().resume().visible().get();
    }

    @After
    public void cleanup() {
        unit = null;
        controller.pause().stop().destroy();
        controller = null;
    }

    @Test
    public void testOnAddClicked() {
        Assert.assertEquals(additionExpectedResult.size(), additionTestData.size());
        for (int i = 0; i < additionExpectedResult.size(); i++) {
            final Pair<Float, Float> data = additionTestData.get(i);
            unit.onAddClicked(data.first, data.second);
            Assert.assertEquals(
                String.valueOf(additionExpectedResult.get(i)),
                ShadowToast.getTextOfLatestToast());
        }
    }
}
