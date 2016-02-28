package com.mobiquity.androidunittests;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is an example of a class that might run
 */
public class RunTasksOnExecutor {
    /** ExecutorService for running background tasks. */
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Method that runs an IO task on a background task.
     * This might be extracting a zipped archive or copying a large
     * file to another directory.
     */
    public void runIOBackgroundTask() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //this run some long task on the background.
            }
        });
    }
}
