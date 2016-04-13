package com.mobiquity.androidunittests.sample;

import com.mobiquity.androidunittests.testutil.MockExecutorService;
import com.mobiquity.androidunittests.testutil.ReflectionUtil;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test of the RunTasksOnExecutor class.
 */
public class RunTasksOnExecutorTests {
    /**
     * RunTasksOnExecutor that is under test.
     */
    private RunTasksOnExecutor unit;
    /**
     * Mock version of ExecutorService.
     */
    private MockExecutorService mockExecutor;

    /*
     * Notice here we are replacing the real executor with our mock executor.
     * This Allows us to control when and how the threaded code is run so we control
     * the moving parts of a test. Often you will find that threads in unit tests can cause failures
     * due to timing. Maybe you sleep for 1 seconds and sometimes on fast machines that is long
     * enough to see results, but maybe on slow machines it takes 2 seconds. You don't want to
     * constantly have to tuning the timing on a unit test. So instead of that, just control the
     * timing.
     */
    @Before
    public void setup() {
        unit = new RunTasksOnExecutor();
        mockExecutor = new MockExecutorService();
        ReflectionUtil.setField(unit, "executor", mockExecutor);
    }

    @After
    public void after() {
        mockExecutor = null;
        unit = null;
    }

    /*
     * This is a simple test. Just check the pending count and run a method.
     * Were just making sure that the method executes something. A more involved test
     * might also check that a specific class of runnable in pending. Or that the results or running
     * it are as expected. I would say that if you have a concrete runnable class then test that
     * separately but if it's an anonymous runnable then test it here.
     */
    @Test
    public void testRunIOBackgroundTask() {
        Assert.assertEquals(0, mockExecutor.getPendingCount());
        unit.runIOBackgroundTask();
        Assert.assertEquals(1, mockExecutor.getPendingCount());
        mockExecutor.runAllTasks();
        Assert.assertEquals(0, mockExecutor.getPendingCount());
        unit.runIOBackgroundTask();
        unit.runIOBackgroundTask();
        Assert.assertEquals(2, mockExecutor.getPendingCount());
        mockExecutor.runAllTasks();
    }
}
