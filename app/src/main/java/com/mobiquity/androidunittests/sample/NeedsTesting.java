package com.mobiquity.androidunittests.sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Stupid example of a class that might need testing.
 *
 * README:
 *     This class is stupid. It's meant as a simple example.
 *     Hopefully I can come up with a better example in the future and provide something more
 *     concrete.
 *     The example itself is useful as it can provide a null safe Integer list. As long as values
 *     are added and removed from the interface you know it will not have a null.
 *
 *     handle(int): This method does evil and confusing things. But it makes a good example here.
 *                  It takes a value and adds it to the list if it's already not there or removes it
 *                  if it is already there. This is why it's confusing. The caller has no way of
 *                  knowing the behavior of this method unless it knows the state of the list.
 *
 *     get() & get(int, int): Gets a filtered version of this list where the values are in
 *                            [min, max]. get() uses the default range of [0, 100].
 *
 *     clearList(): does what it says, clears the list, makes sense.
 *
 *     isInRange(int, int, int): Method is unnecessary honestly, but I needed a private method
 *                               example.
 */
public class NeedsTesting {
    /** Max value allowed in list. */
    private static final int MAX = 100;
    /** Min value allowed in list. */
    private static final int MIN = 0;
    /** List of values being stored. */
    private final List<Integer> integerList = new ArrayList<>();

    /**
     * Method for handling values being passed the class that needs testing.
     * This method will add valid values to the list, or remove them if they already exist.
     * NOTE: This is an example of a BAD method as the result of it is unknown at any time.
     * It's better to have a add method and remove method explicitly. But it makes for a good
     * example of a test.
     *
     * @param value value to handle.
     */
    public void handle(final int value) {
        if (integerList.contains(value)) {
            integerList.remove(integerList.indexOf(value));
        } else {
            integerList.add(value);
        }
    }

    /**
     * Gets a list of values within the default range.
     *
     * @return a list of values within the default range.
     */
    public List<Integer> get() {
        return get(MIN, MAX);
    }

    /**
     * Gets a list of values within the specified range.
     *
     * @param min min value of range.
     * @param max max value of range.
     * @return a list of values within the specified range.
     */
    public List<Integer> get(final int min, final int max) {
        if (max < min) {
            throw new IllegalArgumentException(
                String.format("Max(%d) should not be less than Min(%d)", max, min));
        }
        final List<Integer> result = new ArrayList<>();
        for (final Integer value : integerList) {
            if (!isInRange(value, min, max)) continue;
            result.add(value);
        }
        return result;
    }

    /**
     * private method to clear the list.
     */
    protected void clearList() {
        integerList.clear();
    }

    /**
     * Private method for testing if a value is within the range.
     *
     * @param value value to test.
     * @param min min value of range.
     * @param max max value of range.
     * @return true if the value is within the range. false otherwise.
     */
    private static boolean isInRange(final int value, final int min, final int max) {
        return min <= value && value <= max;
    }
}
