package com.mobiquity.androidunittests.sample;


import com.mobiquity.androidunittests.testutil.ReflectionUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Test of {@link NeedsTesting}.
 */
public class NeedsTestingTest {
    /**
     * Instance object under test.
     */
    private NeedsTesting instance;
    /**
     * Reference to internal list.
     */
    private List<Integer> integerList;
    /**
     * Reference to internal class default .
     */
    private int min;
    /**
     * Reference to internal class default .
     */
    private int max;

    /*
     * Any method annotated with @Before will get run before each test.
     * There can be many methods annotated with @Before they will all be ran, however,
     * the order in which they run are not guaranteed. Generally you only need one.
     */
    @Before
    public void setup() {
        instance = new NeedsTesting();
        // use reflection to get access to internal list.
        integerList = ReflectionUtil.getField(instance, "integerList");
        // check assumptions that list is not null and is empty.
        Assert.assertNotNull(integerList);
        Assert.assertTrue(integerList.isEmpty());
        min = ReflectionUtil.getStaticField(NeedsTesting.class, "MIN");
        max = ReflectionUtil.getStaticField(NeedsTesting.class, "MAX");
    }

    /*
     * @After is the same as before except they run after the test. (dun!)
     * I want to point out the order they appear in the class definition here.
     * This is one area where I feel code locality helps. I've put the before and after methods
     * right next to each other. I feel it helps when trying to understand a test class
     * too see relevant code close together.
     */
    @After
    public void cleanup() {
        integerList.clear();
        integerList = null;
        instance = null;
    }

    /*
     * Tests are annotated with @Test. Same as with @Before and @After there can be many and
     * the order they run is not guaranteed. Tests should be able to run independently.
     */
    @Test
    public void explanation() {
        // Part one: You should try to validate any assumptions you have. If assumptions change this
        //           may break the code or invalidate the test. Checking assumptions before a test
        //           ensures that when the assumptions change the test will fail and require
        //           updating. So first check those assumptions to ensure the test code stays up to
        //           date. You can always change assumptions later.
        Assert.assertNotNull(instance);
        Assert.assertNotNull(integerList);
        Assert.assertTrue(integerList.isEmpty());

        Assert.assertEquals(0, min);
        Assert.assertEquals(100, max);

        // Part Two: Configure test. Some of this may be done in the @Before but not every test will
        //           need the same set up. Here you want to prepare your data. You will notice in
        //           testGet() and testClearList() I call prepareData() to set up some data.

        // prepare some data.
        prepareData();
        // Remember Part 1? Well we are assuming data was added to the list, check that assumption!
        Assert.assertEquals(7, integerList.size());

        // Part Three: Test. Runs some tests. Call methods ect then check results and assumptions.
        System.out.println("These tests will provide some confidence that the code works.");

        // Part Four: Clean up. Not every test will have the same set up, so not every test will
        //            have the same clean up. It's important to make sure you clean up because what
        //            you leave around could effect other tests. Now, if the set ups are
        //            significantly different then it should probably be in another test class.
    }

    @Test
    public void testHandle() {
        // check assumptions that list is not null and is empty.
        Assert.assertNotNull(integerList);
        Assert.assertTrue(integerList.isEmpty());

        // test the method.
        instance.handle(-1);
        Assert.assertEquals(1, integerList.size());
        instance.handle(0);
        Assert.assertEquals(2, integerList.size());
        instance.handle(2);
        Assert.assertEquals(3, integerList.size());
        instance.handle(100);
        Assert.assertEquals(4, integerList.size());
        instance.handle(101);
        Assert.assertEquals(5, integerList.size());

        instance.handle(101);
        Assert.assertEquals(4, integerList.size());

        instance.handle(2);
        Assert.assertEquals(3, integerList.size());
        instance.handle(2);
        Assert.assertEquals(4, integerList.size());
    }

    @Test
    public void testGet() {
        // check assumptions that list is not null and is empty.
        Assert.assertNotNull(integerList);
        Assert.assertTrue(integerList.isEmpty());

        // Test that get returns an empty list
        final List<Integer> initial = instance.get();
        Assert.assertNotNull(initial);
        Assert.assertTrue(initial.isEmpty());

        // prepare some data.
        prepareData();
        // assuming data adds to the list, check assumption
        Assert.assertEquals(7, integerList.size());

        // test the default version
        final List<Integer> result = instance.get();
        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.size());
        assertAllInRange(result, min, max);

        // test the parametrized version.
        final List<Integer> rangeResult = instance.get(3, 100);
        Assert.assertNotNull(rangeResult);
        Assert.assertEquals(4, rangeResult.size());
        assertAllInRange(rangeResult, 3, 100);

        // test that bad input results in an exception.
        try {
            instance.get(11, 10);
            Assert.fail(
                    "NeedsTesting#get(int, int) should throw an IllegalArgumentException if min > max");
        } catch (final IllegalArgumentException e) {
            Assert.assertEquals("Max(10) should not be less than Min(11)", e.getMessage());
        }
    }

    @Test
    public void testClearList() {
        // check assumptions that list is not null and is empty.
        Assert.assertNotNull(integerList);
        Assert.assertTrue(integerList.isEmpty());

        // prepare some data.
        prepareData();
        // assuming data adds to the list, check assumption
        Assert.assertFalse(integerList.isEmpty());

        // test it.
        // clearList() is protected, but since the test is in the same package it can call it.
        instance.clearList();
        Assert.assertTrue(integerList.isEmpty());
    }

    @Test
    public void testIsInRange() {
        final Method isInRange = ReflectionUtil.getMethod(
                NeedsTesting.class, "isInRange", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        Assert.assertNotNull(isInRange);
        Assert.assertTrue((boolean) ReflectionUtil.invokeMethod(isInRange, isInRange, 1, 0, 2));
        Assert.assertFalse((boolean) ReflectionUtil.invokeMethod(isInRange, isInRange, -1, 0, 2));
        Assert.assertFalse((boolean) ReflectionUtil.invokeMethod(isInRange, isInRange, 3, 0, 2));
        Assert.assertTrue((boolean) ReflectionUtil.invokeMethod(isInRange, isInRange, 0, 0, 2));
        Assert.assertTrue((boolean) ReflectionUtil.invokeMethod(isInRange, isInRange, 2, 0, 2));
    }

    /**
     * Helper method for asserting that every value in the list is within the range.
     *
     * @param values list of values to test.
     * @param min    min value in range.
     * @param max    max value in range.
     */
    private void assertAllInRange(final List<Integer> values, final int min, final int max) {
        for (final int value : values) {
            Assert.assertTrue(min <= value);
            Assert.assertTrue(value <= max);
        }
    }

    /**
     * prepare same data for a test.
     */
    private void prepareData() {
        instance.handle(-1);
        instance.handle(0);
        instance.handle(10);
        instance.handle(50);
        instance.handle(75);
        instance.handle(100);
        instance.handle(101);
    }
}
