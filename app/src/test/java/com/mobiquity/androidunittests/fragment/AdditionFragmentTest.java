package com.mobiquity.androidunittests.fragment;

import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobiquity.androidunittests.BuildConfig;
import com.mobiquity.androidunittests.R;
import com.mobiquity.androidunittests.activity.MainActivity;
import com.mobiquity.androidunittests.util.ReflectionUtil;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test of the AdditionFragment.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.mobiquity.androidunittests")
public class AdditionFragmentTest {
    /** ActivityController for Activity. */
    private ActivityController<MainActivity> controller;
    /** AdditionFragment under test. */
    private AdditionFragment unit;

    /** Augend EditText from fragment. */
    private EditText augend;
    /** Addend EditText from fragment. */
    private EditText addend;
    /** Add Button from fragment. */
    private Button add;
    /** Set of test data for the addition test. */
    private List<Pair<Float, Float>> additionTestData = new ArrayList<>();
    {
        additionTestData.add(new Pair<>(0f, 10f));
        additionTestData.add(new Pair<>(0f, Float.NaN));
        additionTestData.add(new Pair<>(Float.NaN, 0f));
        additionTestData.add(new Pair<>(Float.NaN, Float.NaN));
        additionTestData.add(new Pair<>(10f, -10f));
        additionTestData.add(new Pair<>(20f, -10f));
    }

    @Before
    public void setup() {
        controller = Robolectric.buildActivity(MainActivity.class);
        controller.create().start().resume().visible();
        final FragmentManager manager = controller.get().getSupportFragmentManager();
        unit = (AdditionFragment) manager.findFragmentById(R.id.addition);
        final View view = unit.getView();
        Assert.assertNotNull(view);
        augend = (EditText) view.findViewById(R.id.augend);
        addend = (EditText) view.findViewById(R.id.addend);
        add = (Button) view.findViewById(R.id.add);
    }

    @After
    public void cleanup() {
        add = null;
        addend = null;
        augend = null;
        unit = null;
        controller.pause().stop().destroy();
        controller = null;
    }

    @Test
    public void testAddButton() {
        // It might be tempting to use a random to generate the test data for this test. However,
        // that would make the test non-deterministic. So use planned data.
        final AtomicInteger index = new AtomicInteger();
        // Check it out were going to use reflection to simplifying this again!
        // This will replace the activity callback with our test one.
        // We want to assert that the callback gets called with the expected data.
        ReflectionUtil.setField(unit, "listener", new AdditionFragment.AdditionFragmentListener() {
            @Override
            public void onAddClicked(float augend, float addend) {
                final Pair<Float, Float> data = additionTestData.get(index.get());
                Assert.assertEquals(data.first, augend);
                Assert.assertEquals(data.second, addend);
            }
        });

        for (; index.intValue() < additionTestData.size(); index.incrementAndGet()) {
            final Pair<Float, Float> data = additionTestData.get(index.get());
            augend.setText(String.valueOf(data.first));
            addend.setText(String.valueOf(data.second));
            // Our listener will be asserting that it received the excepted data.
            add.performClick();
        }
    }
}
